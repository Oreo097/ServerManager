public class Message {

    public String origin_message;
    public String message;
    public int comfrom;
    public int address;

    public Message next_message;

    public Message(String m_message){
        origin_message=m_message;
        int confrom=Integer.valueOf(origin_message.substring(4));
        int address=Integer.valueOf(origin_message.substring(5,9));
        message=origin_message.substring(10);
    }

    public static Message dispose_message(String m_message){//从原始信息中获取发信端和接收端信息
        Message message=new Message(m_message);
        return message;
    }
}

