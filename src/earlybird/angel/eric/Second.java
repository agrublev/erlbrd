package earlybird.angel.eric;

import java.util.*;

public class Second {
		int time = 0;
		float x = 0;
		float y = 0;
		float z = 0;
		float total = 0;
		int freq = 0;
		
		public Second(ArrayList<float[]> array){
			this.freq = array.size();
			for (int i = 0; i < array.size() - 1; i++){
				add(array.get(i), array.get(i+1));
			}
		}
			
		public void add(float[] dat1, float[] dat2){
			this.x += Math.sqrt( (sq(dat1[0] -  dat2[0])));
			this.y += Math.sqrt( (sq(dat1[1] -  dat2[1])));
			this.z += Math.sqrt( (sq(dat1[2] -  dat2[2])));
			total = x + y + z;
		}
		
		public float getX(){
			return this.x;
		}
			
		public float getY(){
			return this.y;
		}
			
		public float getZ(){
			return this.z;
		}
			
		public int getF(){
			return this.freq;
		}
		
		public float sq(float x){
			float z = x * x;
			return z;
			
		}
}
