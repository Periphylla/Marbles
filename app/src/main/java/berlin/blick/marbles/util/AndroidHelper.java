package berlin.blick.marbles.util;

/**
 * Provides Android methods I need, but for some reasons do not work on my mobile.
 */
public class AndroidHelper {

    public static int[] arrayCopy(int[] source, int startPos, int size) {
        final int[] result = new int[size];
        System.arraycopy(source, startPos, result, 0, size);
//        for (int i = 0; i < size; i++) {
//            result[i] = source[startPos + i];
//        }
        return result;
    }
}
