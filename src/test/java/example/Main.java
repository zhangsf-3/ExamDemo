package example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.migu.schedule.info.TaskInfo;

public class Main
{
	public static void main(String[] args) {  
        int[] a = { 1, 2, 3, 4, 5};  
          
        int begin = 0; //从哪里开始选  
        int selectNum = 4; //选择几个  
          
        int mid = begin + selectNum;  
        if(mid <= a.length)  
            permutation(a, begin, begin, mid, mid, a.length, selectNum);  
  
        int[][] b = new int[collect.size()][]; // 打印验证  
        collect.toArray(b);  
        for (int i = 0; i < b.length; i++) {  
            int[] nums = b[i];  
            for (int j = 0; j < nums.length; j++) {  
                System.out.print(nums[j]);  
            }  
            System.out.println();  
        }  
    }  

    
    public static List<int[]> collect = new ArrayList<int[]>();

	public static void permutation(int[] a, int begin0, int begin, int mid1, int mid2, int end, int selectNum) {

		int[] temp = new int[selectNum];
		System.arraycopy(a, begin0, temp, 0, selectNum);
		collect.add(temp);

		for (int t = begin; t < mid1; t++) {
			for (int j = mid2; j < end; j++) {
				int temp0 = a[t];
				a[t] = a[j];
				a[j] = temp0;
						
				permutation(a, begin0, t + 1, mid1, j + 1, end, selectNum);
				
				a[j] = a[t];
				a[t] = temp0;
			}
		}

	}

}
