public class MessagePool {
    public Message[] messages_rece;
    public Message[] messages_send;
    public int message_send_pointer=-1;
    public int message_rece_pointer=-1;

    //使用时需要使用线程锁锁住
}
