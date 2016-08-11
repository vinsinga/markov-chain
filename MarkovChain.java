import java.util.*;
import java.util.concurrent.*;

public class MarkovChain {

  public static void main (String[] args) {

    Scanner scan = new Scanner(System.in);

    char[] input = readChars(scan);
    Hashtable hash = makeHash(input);

    int space = (int) hash.get(' ');

    Double[][] index = trainChain(input, hash);
    List<Integer> newSeries = genNewSeries(ThreadLocalRandom.current().nextInt(0, hash.size()), space, index);
    String word = seriesToString(newSeries, hash);

    System.out.println(word);

  }

  static char[] readChars (Scanner scan) {

    char[] input = scan.nextLine().toCharArray();

    return input;

  }

  static Hashtable makeHash (char[] input) {

    Hashtable hash = new Hashtable();

    for (char item : input) {

      if (hash.containsKey(item) == false) {

        hash.put(item, hash.size());

      }

    }

    return hash;

  }

  static Double[][] trainChain (char[] input, Hashtable hash) {

    Double[][] index = new Double[hash.size()][hash.size()];

    int size = hash.size();

    int last = (int) hash.get(input[0]);
    int next;

    Double total;

    int j;
    int i = 0;

    while (i < size) {

      j = 0;

      index[i][i] = 0.00;

      while (j < size) {

        index[i][j] = 0.00;

        j++;
      }

      i++;

    }

    for (char item : input) {

      next = (int) hash.get( item);

      index[last][last] = index[last][last] + 1;
      index[last][next] = index[last][next] + 1;

      last = next;

    }

    i = 0;

    while (i < size) {

      total = index[i][i];

      if (total > 0) {

        j = 0;

        while (j < size) {

          if (j != i) { index[i][j] = index[i][j] / total; }

          j++;

        }
      }

      i++;

    }
    return index;

  }

  static List<Integer> genNewSeries (int start, int stop, Double[][] index) {

    List<Integer> newSeries = new ArrayList<Integer>();

    Integer last = stop;
    Integer next = 0;

    int j;
    int i = 0;

    boolean noPass;

    while (last == stop) {

      last = ThreadLocalRandom.current().nextInt(0, index.length);

    }

    newSeries.add(last);

    while (next != stop) {

      noPass = true;

      while (noPass) {

        j = 0;

        while (j < index[last].length) {


          if (ThreadLocalRandom.current().nextDouble(1) < index[last][j] && j != next) {

            next = j;
            noPass = false;
            j = index[last].length;

          }

          j++;

        }

      }

      newSeries.add(next);

      last = next;

    }
    return newSeries;

  }

  static String seriesToString (List<Integer> series, Hashtable hash) {

    String str = "";

    // Reverse hashtable

    Hashtable rHash = new Hashtable();

    for (Object key : hash.keySet()) {

      rHash.put(hash.get(key), key);

    }

    for (Integer num : series) {

      str = str + rHash.get(num);

    }

    return str;

  }

}
