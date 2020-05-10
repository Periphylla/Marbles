package berlin.blick.marbles.engine;


import berlin.blick.marbles.util.AndroidHelper;

public class DijkstraCalculator {

    private static final int[] EMPTY = new int[0];
    
    private final boolean[] _visited;
    private final int[] _distance;
    private final int[] _previous;
    private final int[] _results;
    private final int _size;

    public DijkstraCalculator(int size) {
        _size = size;
        _visited = new boolean[_size];
        _distance = new int[_size];
        _previous = new int[_size];
        _results = new int[_size];
    }
    
    public int[] retrievePath(int from, int to, DijkstraAgent agent) {
        if (from < 0 || from >= _size) throw new AssertionError();
        if (to < 0 || to >= _size) throw new AssertionError();
        assert agent != null;
        int resultsFound = 0;
        reset();
        _previous[from] = from;
        _distance[from] = 0;
        visitClosest(agent); // that's where the magic happens
        // interpret results
        int current = to;
        do {
            if (_previous[current] != -1) {
                _results[_size - resultsFound - 1] = current;
                resultsFound++;
            }
            current = _previous[current];
        } while (current != -1 && current != from);
        if (resultsFound == 0) {
            return EMPTY;
        }
        final int[] result = AndroidHelper.arrayCopy(_results, _size - resultsFound - 1, resultsFound + 1);
        result[0] = from;
        return result;
    }

    private void visitClosest(DijkstraAgent agent) {
        int closestIndex, closestDistance;
        do {
            closestIndex = -1;
            closestDistance = Integer.MAX_VALUE;
            for (int i = 0; i < _size; i++) {
                if (!_visited[i] && _distance[i] < closestDistance) {
                    closestIndex = i;
                    closestDistance = _distance[i];
                }
            }
            if (closestIndex != -1) {
                visit(closestIndex, agent);
            }
        } while (closestIndex != -1);
    }
    
    private void visit(int ancor, DijkstraAgent agent) {
        _visited[ancor] = true;
        final int[] neighbours = agent.getNeighbours(ancor);
        for (int neighbour : neighbours) {
            final int distance = _distance[ancor] + agent.getDistance(ancor, neighbour);
            if (_distance[neighbour] > distance) {
                _distance[neighbour] = distance;
                _previous[neighbour] = ancor;
            }
        }
    }

    private void reset() {
        for (int i = 0; i < _size; i++) {
            _distance[i] = Integer.MAX_VALUE;
            _previous[i] = -1;
            _results[i] = -1;
            _visited[i] = false;
        }
    }

    public interface DijkstraAgent {
        int getDistance(int from, int to);
        int[] getNeighbours(int current);
    }
}