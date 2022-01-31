import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Graph {
    private final int N, K;
    private final Node[] nodes;
    private int maxFlow = 0;

    public Graph(int[][] links) {
        N = links[0][0];
        K = links[0][1];
        nodes = new Node[N];

        // Create nodes
        for (int i = 0; i < N; i++) {
            nodes[i] = new Node(String.valueOf(i));
        }

        // Add links
        for (int i = 1; i <= K; i++) {
            Node thisNode = nodes[links[i][0]];
            Node nextNode = nodes[links[i][1]];

            Link forwardLink = new Link(nextNode, links[i][2], links[i][2]);
            Link reversedLink = new Link(thisNode, links[i][2], 0);

            if (thisNode.getLinks().contains(forwardLink)) {
                thisNode.getLinks().get(thisNode.getLinks().indexOf(forwardLink)).setFlow(links[i][2], false);
            }

            forwardLink.setReverse(reversedLink);
            reversedLink.setReverse(forwardLink);

            thisNode.addLink(forwardLink);
            nextNode.addLink(reversedLink);
        }
    }

    public Node[][] edKarp() {
        ArrayList<Node[]> paths = new ArrayList<>();
        ArrayList<Node> singlePath;

        while ((singlePath = findAugPath(paths)) != null) {
            paths.add(singlePath.toArray(new Node[0]));
            resetNodeFounds();
        }

        return paths.toArray(new Node[0][0]);
    }

    private void resetNodeFounds() {
        for (Node node : nodes) {
            node.setFound(false);
        }
    }

    private ArrayList<Node> findAugPath(ArrayList<Node[]> foundPaths) {
        Node startNode = nodes[0];
        Node endNode = nodes[nodes.length-1];
        // initiate search
        startNode.setFound(true);

        // Search
        LinkedList<Node> nodesToCheck = new LinkedList<>();
        nodesToCheck.add(startNode);

        while (!nodesToCheck.isEmpty()) {
            Node thisNode = nodesToCheck.poll();
            for (Link link : thisNode.getLinks()) {
                if (link.getFlow() == 0) {
                    continue;
                }
                Node linkedNode = link.getLinkedTo();
                if (!linkedNode.isFound()) {
                    linkedNode.setChainedNode(thisNode);
                    linkedNode.setFound(true);
                    nodesToCheck.add(linkedNode);
                }
                if (linkedNode == endNode) {
                    ArrayList<Node> path = linkedNode.nodeChain(new ArrayList<>());
                    Collections.reverse(path);
                    findPathFlow(path);
                    return path;
                }
            }
        }
        return null;
    }

    private void findPathFlow(ArrayList<Node> path) {
        ArrayList<Link> linkPath = nodesToLinks(path);

        // Find last
        int minFlow = linkPath.get(0).getFlow();

        // Find lowest flow available
        for (Link link : linkPath) {
            if (link.getFlow() < minFlow)
                minFlow = link.getFlow(); // Flow available
        }

        // Update flow capacities based on minFlow
        for (Link link : linkPath) {
            link.setFlow(link.getFlow() - minFlow, false);
        }
        maxFlow += minFlow;
    }

    private ArrayList<Link> nodesToLinks(ArrayList<Node> nodes) {
        ArrayList<Link> linkPath = new ArrayList<>();

        for (int i = 0; i < nodes.size() - 1; i++) {
            for (Link link : nodes.get(i).getLinks()) {
                if (link.getLinkedTo() == nodes.get(i + 1)) {
                    linkPath.add(link);
                }
            }
        }
        return linkPath;
    }

    public void printBFSResult() {
        System.out.println("Node --- Predecessor --- Distance");

        for (Node node : nodes) {
            System.out.print(" " + node + "      ");
            System.out.print((node.getChainedNode() == null ? "    none"
                            :  "      " + node.getChainedNode() + " ") + "           ");
            System.out.println();
        }
    }

    public int numberOfLinks() {
        int nodeAmount = 0;
        for (Node node : nodes) {
            nodeAmount += node.numberOfLinks();
        }
        return nodeAmount;
    }

    public void printLinks() {
        for (Node node : nodes) {
            for (Link link : node.getLinks()) {
                System.out.println(node + " --> " + link.getLinkedTo()
                        + ", flowCapacity: " + link.getFlowCapacity()  + " flow: " + link.getFlow());
            }
        }
    }

    public int getMaxFlow() {
        return maxFlow;
    }

    public Node[] getNodes() {
        return nodes;
    }
}
