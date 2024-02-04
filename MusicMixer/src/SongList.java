import java.io.*;

public class SongList {
    private int size;
    private SongRecord [] list;
    private int maxSize;

    public SongList() {
        this.size = 0;
        this.maxSize = 0;
        this.list = new SongRecord[maxSize];
    }


    /**
     * consrtucors that initializes the list to
     * the contents in a file
     * @param fileName
     */
    public SongList(String fileName) throws IOException {
        readFromFile(fileName, true);
    }

    public int getSize() {
        return this.size;
    }

    /**
     * getRecord Method
     * gets the record at a particular index
     */
    public SongRecord getSongRecord(int i) {
        return list[i];
    }

    public boolean insert(SongRecord record) {
        // if the size is greater than or equal to maxSize
        if (getSize() >= maxSize) {
            // increase the max size by however many elements are over the maxsize
            increaseSize(size-maxSize+1);

        }

        // if there is space
        if (size < maxSize) {
            size++;  // increase by 1
            list[size - 1] = record;

            return true; // successful
        }

        return false; // not successful
    }


    public boolean delete(SongRecord record) {
        // get the location from the binary search method
        int location = binarySearch(record.getFileName());

        // if the location is found
        if(location >= 0) {
            // delete the list at location
            list[location] = list[size-1];
            size--;

            // sort the records by customer name
            insertionSort('a');

            return true; // if successful
        }
        return false; // if not successful
    }

    public boolean increaseSize(int sizeToAdd) {
        // if sizeToAdd plus max size is greater than the maxSize
        if(sizeToAdd + maxSize > maxSize) {
            // create a newList with the new size
            SongRecord [] newList = new SongRecord[sizeToAdd+maxSize];
            for(int i = 0; i<size; i++) {
                newList[i] = list[i]; // make the new list equal to the old list
            }

            // equal the new list to the current list
            list = newList;
            maxSize += sizeToAdd; // change the max size to the new size
            return true;
        }

        return false;
    }


    public void insertionSort(char type) {
        // Iterate through the array starting from the second element (index 1)
        for (int top = 1; top < size; top++) {
            // get the current record for comparison
            SongRecord record = list[top];
            int i = top;

            if(type == 'a') {
                // while i is more than 0 and record to compare is greater than the record before it
                while(i > 0 && record.getFileName().compareToIgnoreCase
                        (list[i-1].getFileName()) < 0) {
                    // Shift the elements to make room for the current element
                    list[i] = list[i-1];
                    i--;
                }
            }
            else {
                // while i is more than 0 and record to compare is greater than the record before it
                while(i > 0 && record.getFileName().compareToIgnoreCase
                        (list[i-1].getFileName()) > 0) {
                    // Shift the elements to make room for the current element
                    list[i] = list[i-1];
                    i--;
                }
            }


            // Place the current element in its correct sorted position
            list[i] = record;
        }
    }

    /**
     * Quick sort method to sort by ascending or descneding model year
     */
    public void quickSort(int l, int h, char type) {
        // while the low is less than high
        if(l < h) {
            // partition the array in two by calling the partition method and
            // get the index of the partition by calling partition method
            // with the parameters passed in as l and h
            int j = partition(l, h, type);

            // quickSort the array from low to the partitioned index -1
            quickSort(l, j-1, type);

            // quickSort the array from the partitioned index+1 to the high
            quickSort(j+1, h, type);
        }
    }


    /**
     * Partition method to set the left-most element (pivot) in the correct spot
     * all numbers less than pivot are on the left & all numbers greater than pivot
     * are on the right
     * Takes in the lowest and the highest element's index
     */
    private int partition(int l, int h, char type) {
        // choose the right most element of the array as the pivot
        long pivot = list[h].getFileSize();

        // index of smaller element
        // indicates the right position of pivot found so far
        int i = l-1;

        // loop through the list from l to h-1
        for(int j = l; j <= h -1; j++) {

            if(type == 'a') {
                // if current element is smaller than the pivot
                if(list[j].getFileSize() < pivot) {
                    i++; 	// increment the index of smaller element

                    // swap the record
                    SongRecord temp = list[i];
                    list[i] = list[j];
                    list[j] = temp;
                }
            }
            else {
                // if current element is smaller than the pivot
                if(list[j].getFileSize() > pivot) {
                    i++; 	// increment the index of smaller element

                    // swap the record
                    SongRecord temp = list[i];
                    list[i] = list[j];
                    list[j] = temp;
                }
            }

        }

        // swap the list[i+1] and the pivot (list[h])
        SongRecord temp = list[i+1];
        list[i+1] = list[h];
        list[h] = temp;

        // return the index where the partition is done
        return (i+1);
    }

    public int binarySearch(String searchKey) {
        int low = 0;
        int high = size-1;
        int middle;

        // while the low end is <= high end
        while(low <= high) {
            middle = (high + low)/2;  // divides the array in 2

            // check if the searchKey is found
            if(searchKey.compareToIgnoreCase(list[middle].getFileName()) == 0) {
                return middle;
            }
            // lower end of the alphabet
            else if(searchKey.compareToIgnoreCase(list[middle].getFileName()) < 0) {
                high = middle - 1; // change the high end of the list
            }
            else {
                low = middle + 1; // change the low end of the list
            }
        }

        return -1; // return invalid index if not found
    }

    public void saveToFile(String fileName) throws IOException {
        try {
            FileOutputStream file = new FileOutputStream("src/" + fileName);
            PrintWriter fw = new PrintWriter(file);

            // print how many records there are
            fw.println(getSize());

            // loop through each of the records
            for (int i = 0; i < size; i++) {
                // create a tempe record
                SongRecord tempRecord = list[i];

                // output the customer info, chequing acc info and savings acc info
                // in a formatted string separated by "/"
                fw.println(tempRecord.getFileName() + "/" +
                        tempRecord.getFileSize() + "/" +
                        tempRecord.getDateCreated().toMillis() + "/" +
                        tempRecord.getDateAccessed().toMillis() + "/" +
                        tempRecord.getIsFav());
            }

            fw.close(); // saving the file
        }catch (Exception e) {

        }

    }


    public void readFromFile(String fileToOpen, boolean replace) throws IOException {
        try {
            InputStreamReader fr = new InputStreamReader(getClass().getResourceAsStream(fileToOpen));
            BufferedReader in = new BufferedReader (fr);

            // get the size to add from the first line
            int fileSize = Integer.parseInt(in.readLine());

            // if info needs to be replaced by the info in the file
            if(replace) {
                // set the size from the first line
                size = fileSize;

                // create a new list
                SongRecord [] tempList = new SongRecord[size];

                // get the info and add it to the new list
                for(int i = 0; i<size; i++) {
                    // make a temp record from the line in the file
                    tempList[i] = new SongRecord(in.readLine());

                }

                list = tempList;	// make the main list equal to the tempList
            }

            // if info needs to be added to the existing info
            else {
                // if adding the elements from the file
                // goes over the maxSize of the list
                if(size+fileSize > maxSize) {
                    // increase the maxSize
                    maxSize = size + fileSize;

                    // create a new list with the max size
                    SongRecord [] newList = new SongRecord[maxSize];

                    // add the old info to the new list
                    for(int i = 0; i<size; i++) {
                        newList[i] = list[i];
                    }

                    // update the old list with the new increased list
                    list = newList;

                }

                // loop from the size to the maximum limit and add the records
                for(int i = size; i<size+fileSize;i++) {
                    list[i] = new SongRecord(in.readLine());
                }

                // update the size of the list
                size+=fileSize;

            }

            // close the file
            in.close();
        } catch(Exception e) {

        }


    }

    public String toString() {
        String output = "";
        for(int i = 0; i<getSize(); i++) {
            output += list[i].toString() + "\n";
        }
        return output;
    }


    public static void main(String[] args) {


    }
}
