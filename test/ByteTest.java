import com.prealpha.bytestream.Utility;


public class ByteTest {
	public static void main(String... args){
		byte b = Utility.intToUnsignedByte(777);
		
		print(Utility.toHex(b));
	}
	
	public static void print(Object o){
		System.out.println(o);
	}
}
