public class Message {

    public String origin_message;
    public String message;
    public String comfrom;
    public String address;

    public Message next_message;

    public Message(String m_message){
        origin_message=m_message;
    }

    public static Message dispose_message(String m_message){//从原始信息中获取发信端和接收端信息
        Message message=new Message(m_message);
        return message;
    }
}

