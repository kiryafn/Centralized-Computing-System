import java.util.concurrent.atomic.AtomicLong;

public class Data implements Runnable {
    private static final AtomicLong connectedClients = new AtomicLong(0);
    private static final AtomicLong computedRequests = new AtomicLong(0);
    private static final AtomicLong addOperations = new AtomicLong(0);
    private static final AtomicLong subOperations = new AtomicLong(0);
    private static final AtomicLong mulOperations = new AtomicLong(0);
    private static final AtomicLong divOperations = new AtomicLong(0);
    private static final AtomicLong incorrectOperations = new AtomicLong(0);
    private static final AtomicLong sumOfResults = new AtomicLong(0);

    private final AtomicLong connectedClientsLast10Seconds = new AtomicLong(0);
    private final AtomicLong computedRequestsLast10Seconds = new AtomicLong(0);
    private final AtomicLong addOperationsLast10Seconds = new AtomicLong(0);
    private final AtomicLong subOperationsLast10Seconds = new AtomicLong(0);
    private final AtomicLong mulOperationsLast10Seconds = new AtomicLong(0);
    private final AtomicLong divOperationsLast10Seconds = new AtomicLong(0);
    private final AtomicLong incorrectOperationsLast10Seconds = new AtomicLong(0);
    private final AtomicLong sumOfResultsLast10Seconds = new AtomicLong(0);

    public void incrementConnectedClients() {
        connectedClients.incrementAndGet();
        connectedClientsLast10Seconds.incrementAndGet();
    }

    public void incrementComputedRequests() {
        computedRequests.incrementAndGet();
        computedRequestsLast10Seconds.incrementAndGet();
    }

    public void incrementAddOperations() {
        addOperations.incrementAndGet();
        addOperationsLast10Seconds.incrementAndGet();
    }

    public void incrementSubOperations() {
        subOperations.incrementAndGet();
        subOperationsLast10Seconds.incrementAndGet();
    }

    public void incrementMulOperations() {
        mulOperations.incrementAndGet();
        mulOperationsLast10Seconds.incrementAndGet();
    }

    public void incrementDivOperations() {
        divOperations.incrementAndGet();
        divOperationsLast10Seconds.incrementAndGet();
    }

    public void incrementIncorrectOperations() {
        incorrectOperations.incrementAndGet();
        incorrectOperationsLast10Seconds.incrementAndGet();
    }

    public void addToSumOfResults(int value) {
        sumOfResults.addAndGet(value);
        sumOfResultsLast10Seconds.addAndGet(value);
    }

    public String getData() {
        return "The number of connected clients since start: " + connectedClients.get() + "\n" +
                "The number of computed requests since start: " + computedRequests.get() + "\n" +
                "The numbers of particular requested operations since start:" + "\n" +
                "Add operations: " + addOperations.get() + "\n" +
                "Sub operations: " + subOperations.get() + "\n" +
                "Mul operations: " + mulOperations.get() + "\n" +
                "Div operations: " + divOperations.get() + "\n" +
                "The number of incorrect operations since start: " + incorrectOperations.get() + "\n" +
                "The sum of computed values since start: " + sumOfResults.get();
    }

    public String getRecentData() {
        return "The number of newly connected clients (last 10 sec): " + connectedClientsLast10Seconds.get() + "\n" +
                "The number of computed requests (last 10 sec): " + computedRequestsLast10Seconds.get() + "\n" +
                "The numbers of particular requested operations (last 10 sec):" + "\n" +
                "Add operations: " + addOperationsLast10Seconds.get() + "\n" +
                "Sub operations: " + subOperationsLast10Seconds.get() + "\n" +
                "Mul operations: " + mulOperationsLast10Seconds.get() + "\n" +
                "Div operations: " + divOperationsLast10Seconds.get() + "\n" +
                "The number of incorrect operations: " + incorrectOperationsLast10Seconds.get() + "\n" +
                "The sum of computed values: " + sumOfResultsLast10Seconds.get();
    }

    private void resetRecentData() {
        connectedClientsLast10Seconds.set(0);
        computedRequestsLast10Seconds.set(0);
        addOperationsLast10Seconds.set(0);
        subOperationsLast10Seconds.set(0);
        mulOperationsLast10Seconds.set(0);
        divOperationsLast10Seconds.set(0);
        incorrectOperationsLast10Seconds.set(0);
        sumOfResultsLast10Seconds.set(0);
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Statistics since start:");
                System.out.println(getData());

                System.out.println();
                System.out.println("Statistics from the last 10 seconds:");
                System.out.println(getRecentData());
                System.out.println("====================================================================================================");
                System.out.println();

                resetRecentData();

                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}