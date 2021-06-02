package Algorithms;

public class ZFunct {
	//does z function on string
	public static int[] Zfunct(String s) {
		int[] arr = new int[s.length()];
		for(int i = 1, l = 0, r = 0; i < s.length(); i++) {
			if(i <= r)
				arr[i] = Math.min(r - i + 1, arr[i - l]);
			while(i + arr[i] < s.length() && s.charAt(arr[i]) == s.charAt(i + arr[i]))
				arr[i]++;
			if(i + arr[i] - 1 < r) {
				l = i;
				r = i + arr[i] - 1;
			}
		}
		return arr;
	}
}
