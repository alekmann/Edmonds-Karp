import java.util.Objects;

public class Link {
    private final Node linkedTo;
    private Link reverse;
    private int flowCapacity;
    private int flow;

    public Link(Node linkedTo, int flowCapacity, int flowUsage) {
        this.linkedTo = linkedTo;
        this.flowCapacity = flowCapacity;
        this.flow = flowUsage;
    }

    public Node getLinkedTo() {
        return linkedTo;
    }

    public Link getReverse() {
        return reverse;
    }

    public int getFlowCapacity() {
        return flowCapacity;
    }

    public int getFlow() {
        return flow;
    }

    public void setReverse(Link reverse) {
        this.reverse = reverse;
    }

    public void setFlow(int flow, boolean stop) {
        this.flow = flow;
        if (stop) return;
        if (flow > flowCapacity)
            throw new IllegalArgumentException("Flow " + flow + " exceeds flow capacity to " + linkedTo);
        this.reverse.setFlow(flowCapacity - flow, true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return linkedTo.equals(link.linkedTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(linkedTo);
    }

    @Override
    public String toString() {
        return "--> " + linkedTo;
    }
}