
public class testa3 {
    public static void main(String[] args) throws  BinaryFormatExecption{
        BinaryConverter converter = new BinaryConverter();
        String binary = "10101310";
        int binaryint = converter.bin2Dec(binary);
        System.out.println(binaryint);
    }
    
}
