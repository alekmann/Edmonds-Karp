import java.util.ArrayList;

public class Node {
    private final String name;
    private final ArrayList<Link> links;
    private Node chainedNode;
    private boolean found;

    public Node(String name) {
        this.name = name;
        links = new ArrayList<>();
    }

    public void addLink(Link link) {
        links.add(link);
    }

    public void printNodeChain() {
        System.out.print(this);
        if (chainedNode != null) {
            System.out.print(" - ");
            chainedNode.printNodeChain();
        }
    }

    public void printLinks() {
        System.out.println("Links of " + this + ": ");
        for (Link link : links) {
            System.out.println(this + " --> " + link.getLinkedTo() + ", flowCapacity: " + link.getFlowCapacity());
        }
    }

    public Node getChainedNode() {
        return chainedNode;
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    public void setChainedNode(Node chainedNode) {
        this.chainedNode = chainedNode;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public int numberOfLinks() {
        return links.size();
    }

    public ArrayList<Node> nodeChain(ArrayList<Node> nodes) {
        nodes.add(this);
        if (chainedNode == null)
            return nodes;
        return chainedNode.nodeChain(nodes);
    }

    @Override
    public String toString() {
        return name;
    }
}

