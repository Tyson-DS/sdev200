

public class BinaryConverter{
    public int bin2Dec(String binary) throws BinaryFormatExecption{
        int result=0;
        for (int i=0; i <binary.length(); i++){
            char bin = binary.charAt(i);
            if (bin != '0' && bin != '1'){
                throw new BinaryFormatExecption("");
            }
            else{
            result = result*10+(binary.charAt(i)-'0');
            }
        }
        return result;
    }
}