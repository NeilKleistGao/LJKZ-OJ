package utils;

import dao.ProblemDAO;
import dao.SubmissionDAO;
import dao.UserDAO;
import entity.Problem;
import entity.Submission;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.Base64;

public class Judger {
    private static Judger judger = null;
    private ExecutorService compileService = null, runService = null;
    private final static String COMPILE_COMMAND = "g++ ../temp/filename.cpp -o ../temp/filename -lm -O2";

    private Judger() {
        compileService = Executors.newFixedThreadPool(2);
        runService = Executors.newFixedThreadPool(14);
    }

    public static Judger getInstance() {
        if (judger == null) {
            judger = new Judger();
        }

        return judger;
    }

    public void commit(Submission submission) {
        String id = Base64.getEncoder().encodeToString(
                (submission.getEmail() + submission.getPid() + submission.getSubmitTime().toString()).getBytes());
        Future<String> future = null;
        try {
            PrintWriter writer = new PrintWriter("../temp/" + id + ".cpp");
            writer.print(submission.getSubmitCode());
            writer.close();

            String command = COMPILE_COMMAND.replace("filename", id);
            future = compileService.submit(() -> {
                Process p = Runtime.getRuntime().exec(command);
                InputStream stream = p.getErrorStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                p.waitFor();

                if (p.exitValue() != 0) {
                    return "fail";
                }

                String s = null;
                while ((s = reader.readLine()) != null) {
                    if (s.contains("error")) {
                        return "fail";
                    }
                }

                return "success";
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        boolean flag = true;
        try {
            String res = future.get(5, TimeUnit.SECONDS);
            if ("fail".equals(res)) {
                flag = false;
            }
        }
        catch (Exception e) {
            flag = false;
        }

        if (flag) {
            ArrayList<String> bashList = new ArrayList<>();
            Problem problem = null;
            String dataPath = "";
            int timeLimit = 0;
            try (ProblemDAO dao = new ProblemDAO()) {
                File file = new File("../temp", id + ".cpp");
                file.delete();

                problem = dao.getProblem(submission.getPid());
                dataPath = problem.getDataPath();
                File[] list = new File("../data/" + problem.getDataPath() + "_dir").listFiles();

                for (File testFile : list) {
                    String name = testFile.getName();
                    if (name.endsWith(".in")) {
                        String prefix = name.replace(".in", "");

                        PrintWriter writer = new PrintWriter("../temp/" + id + prefix + ".sh");
                        int memLim = problem.getMemoryLimit() * 512;
                        timeLimit = problem.getTimeLimit();
                        writer.println("ulimit -m " + memLim);
                        writer.println("timeout " + (timeLimit + 0.5f) +
                                " ../temp/" + id +
                                        " < ../data/" + dataPath +
                                        "_dir/" + prefix + ".in > ../temp/" + id + prefix + ".out");
                        writer.println("ulimit -v " + memLim);
                        writer.println("timeout " + (timeLimit + 0.5f) +
                                " ../temp/" + id + " < ../data/" + dataPath + "_dir/" + prefix + ".in" + " > /dev/null");
                        writer.close();

                        bashList.add(prefix);
                        Process pre = Runtime.getRuntime().exec("chmod 777 ../temp/" + id + prefix + ".sh");
                        pre.waitFor();
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            int finalTimeLimit = timeLimit;
            String finalDataPath = dataPath;
            Future<String> future1 = runService.submit(() -> {
                for (String prefix : bashList) {
                    Instant begin = Instant.now();
                    Process process = Runtime.getRuntime().exec("../temp/" + id + prefix + ".sh");
                    process.waitFor();
                    Instant end = Instant.now();

                    if (Duration.between(begin, end).getSeconds() > finalTimeLimit * 2) {
                        return "Tle";
                    }

                    InputStream stream = process.getErrorStream();
                    byte[] bytes = new byte[256];
                    int len, count = 0;
                    while ((len = stream.read(bytes)) != -1) {
                        String s = new String(bytes);
                        if (s.contains("fault")) {
                            count++;
                        }

                    }

                    FileReader reader1 = new FileReader("../temp/" + id + prefix + ".out");
                    FileReader reader2 = new FileReader("../data/" + finalDataPath + "_dir/" + prefix + ".ans");

                    int ch1;
                    while ((ch1 = reader1.read()) != -1) {
                        int ch2 = reader2.read();

                        if (ch2 == -1 && count == 1) {
                            return "Re";
                        }

                        if (ch1 != ch2) {
                            reader1.close();
                            reader2.close();
                            return "Wa";
                        }
                    }

                    if (count == 1) {
                        return "Mle";
                    }
                }
                return "Ac";
            });

            String res = "";
            try {
                res = future1.get(problem.getTimeLimit() * bashList.size(), TimeUnit.SECONDS);
            }
            catch (TimeoutException e) {
                res = "Tle";
            }
            catch (Exception e) {
                res = "Re";
            }

            submission.setState(res);
            try (SubmissionDAO dao = new SubmissionDAO()) {
                dao.insert(submission);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            try (UserDAO dao = new UserDAO()) {
                dao.addNum(submission.getEmail(), res);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            if ("Ac".equals(res)) {
                try (ProblemDAO dao = new ProblemDAO()) {

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            try (SubmissionDAO dao = new SubmissionDAO()) {
                submission.setState("Ce");
                dao.insert(submission);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            try (UserDAO dao = new UserDAO()) {
                dao.addNum(submission.getEmail(), "Ce");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
