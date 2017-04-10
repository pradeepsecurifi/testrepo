class SortBinaryClass {
    void sortBinay(int binArr[]) {
        int start = 0, end = (binArr.length-1);
        while (start < end){
            while (binArr[start] == 0 && start < end)
               start++;
            while (binArr[end] == 1 && start < end)
                end--;
            if (start < end) {
                binArr[start] = 0;
                binArr[end] = 1;
                ++start;
                --end;
            }
        }
    }
    public static void main(String[] args){
        SortBinaryClass seg = new SortBinaryClass();
        int binArr[] = new int[]{1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1};
        seg.sortBinay(binArr);
        for (int i = 0; i < binArr.length; i++){
            System.out.print(binArr[i] + ",");
        }
    }
}