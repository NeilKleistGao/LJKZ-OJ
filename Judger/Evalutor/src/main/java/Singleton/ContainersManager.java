package Singleton;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Container;
import org.apache.commons.lang.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class ContainersManager {
    private static List<Container> closed = new ArrayList<>();
    private static List<Container> open = null;
    public static synchronized Container getCainer() throws DockerException, InterruptedException {
        DockerClient dockerClient = DockerClinetManager.getDockerClient();
        if (open == null) {
            open = dockerClient.listContainers(DockerClient.ListContainersParam.allContainers());;
        }
        if (open.size() > 0) {
            Container c = open.remove(0);
            closed.add(c);
            return c;
        }
        return null;
    }

    public static synchronized void setContainer(String id) {
        DockerClient dockerClient = DockerClinetManager.getDockerClient();
        for (int i = 0; i < closed.size(); i++) {
            if (closed.get(i).id().equals(id)) {
                open.add(closed.remove(i));
                break;
            }
        }
    }
}
