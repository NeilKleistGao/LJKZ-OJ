#include <iostream>
#include <dirent.h>
#include <stdlib.h>
#include<string>
#include "parse.h"
#include "filecompare.h"
#include "result.h"
#include "exit_code.h"
#include "utils.h"
// clear tmp/out
int timeuse = 0,memoryuse = 0,exitCode;
int clearFile(const char *path){
    /* 打开一个文件 */
    int ret = open(path, O_WRONLY | O_TRUNC);
    if(ret == -1)
    {
        printf("open file is fail!\n");
        return -1;
    }
    close(ret);
    return 0;
}
// get  eaxmple count
int get_file_count(const char *root)
{
    DIR *dir;
    struct dirent * ptr;
    int total = 0;
    char path[1024];
    dir = opendir(root); /* 打开目录*/
    if(dir == NULL)
    {
        perror("fail to open dir");
        exit(1);
    }

    errno = 0;
    while((ptr = readdir(dir)) != NULL)
    {
        //顺序读取每一个目录项；
        //跳过“..”和“.”两个目录
        if(strcmp(ptr->d_name,".") == 0 || strcmp(ptr->d_name,"..") == 0)
        {
            continue;
        }
        //如果是目录，则递归调用 get_file_count函数

        if(ptr->d_type == DT_DIR)
        {
            sprintf(path,"%s%s/",root,ptr->d_name);
            total += get_file_count(path);
        }

        if(ptr->d_type == DT_REG)
        {
            total++;
        }
    }
    if(errno != 0)
    {
        printf("fail to read dir");  //失败则输出提示信息
        exit(1);
    }
    closedir(dir);
    return total;
}
int run_all(ini_result iniResult){
    // workspace :
    /*workspace
    ├──
    ├── problem
    │   └── 1
    │       ├── in
    │       │   ├── 1.in
    │       │   └── 2.in
    │       └── out
    │           ├── 1.out
    │           └── 2.out
    ├── parse
    └── test1.cpp
     *
     * */

    std::string workspace = "/ljkz_data";
    std::string indir = workspace + "/" + iniResult.pid;
    std::string outdir = workspace + "/" + iniResult.pid;
    std::string execdir = "/ljkz_code/"+iniResult.exec;
    run_in runIn;
    int count = get_file_count(indir.c_str());
    for(int i=1;i<=count / 2;i++){
        std::string in = indir+"/"+std::to_string(i)+".in";
        std::string out = outdir + "/"+ std::to_string(i)+".out";
//        std::cout << iniResult.outFileTem << std::endl;
//        std::cout << out << std::endl;
        runIn.stderr_file=iniResult.errFileTem;
        runIn.stdout_file=iniResult.outFileTem;
        runIn.stdin_file= in;
        runIn.stdexec_file=execdir;
        run_result runResult = run(runIn);
        if(runResult.mem>memoryuse)memoryuse=runResult.mem;
        if(runResult.time>timeuse)timeuse=runResult.time;
        int isAc;
        if(runResult.exitCode!=EX_SUCCESS){

            return runResult.exitCode;
        }else{
            isAc = file_compare(runIn.stdout_file.c_str(),out.c_str());
            clearFile(runIn.stdout_file.c_str());//清空out文件方便输出
            if(!isAc){
                return EX_WA;
            }
        }
    }
    return EX_AC;
}
int main(int argc, char * argv[]) {
//    test();
    cmp_result cmpResult;
    // 解析参数，./parse -m -t -p : programid
    parse_opt(argc,argv);
    ini_result iniResult = init_env();
    int ex_code =  run_all(iniResult);
    clear_env(iniResult.temDir);
    printf("ex_code=%d&"
           "timeuse=%d&"
           "memoryuse=%d",ex_code,timeuse,memoryuse);
    return ex_code;
}
