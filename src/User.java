import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class User {

    public Socket user_socket;
    public boolean user_checkpoint_main;
    public boolean user_checkpoint_send;
    public boolean user_checkpoint_rece;

    public String message_send;
    public String message_rece;

    public int check_time_send=500;
    public int check_time_rece=500;
    public int check_time_disp=500;

    public Message[] user_message_send_pool;
    public Message[] user_message_rece_pool;

    public int message_pointer_send;
    public int message_pointer_rece;
    public int squence

    private Thread send_thread;
    private Thread rece_thread;
    private Thread watd_thread;

    private OutputStream user_outputstream;
    private InputStream user_inputstream;
    private BufferedReader user_bufferedreader;
    private PrintWriter user_printwriter;

    /*
    构造函数
     */
    public User(Socket m_socket){
        user_socket=m_socket;
        user_checkpoint_main=true;
        user_checkpoint_send=false;
        user_checkpoint_rece=false;

        message_rece="0";
    }

    /*
    log
     */
    Logger logger = Logger.getLogger(User.class.getName());


    /*
    初始化发送函数
     */
    public void init_send(){
        try{
            user_outputstream=user_socket.getOutputStream();
            user_printwriter=new PrintWriter(user_outputstream);
            logger.info("inited send");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /*
    初始化接收函数
     */
    public void init_rece(){
        try{
            user_inputstream=user_socket.getInputStream();
            user_bufferedreader=new BufferedReader(new InputStreamReader(user_inputstream));
            logger.info("inited reaceive");
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /*
    初始化IO
     */
    public void init_io(){
        init_rece();
        init_send();
        logger.info("io has been inited");
    }


    /*
    底层发送消息
     */
    public void send_message(){
        user_printwriter.println(message_send);
        user_printwriter.flush();
        logger.info("message has been sended-> "+message_send);
        message_send="0";
        user_checkpoint_send=false;
        logger.info("user_checkpoint_send has been turned to : "+user_checkpoint_send);
    }


    /*
    底层接收消息
     */
    public void receive_message(){
        try{
            message_rece=user_bufferedreader.readLine();
            logger.info("received message-> "+message_rece);
            user_checkpoint_rece=true;
            logger.info("user_checkpoint_rece has been turn to: "+user_checkpoint_rece);
        }catch(Exception e){
            user_checkpoint_main=false;
            e.printStackTrace();
        }

    }

    /*
    发送服务
     */
    public void service_send(){
        while(user_checkpoint_main){
            if(user_checkpoint_send){
                send_message();
                Function.sleep(check_time_send);
            }
        }
    }
    /*
    接收服务
     */
    public void service_rece(){
        while(user_checkpoint_main){
            if(!user_checkpoint_rece){
                receive_message();
                Function.sleep(check_time_rece);
            }
        }
    }

    /*
    看门狗服务
     */
    public void service_watd(){
        while(true){
            if(!user_checkpoint_main){
                send_thread.interrupt();
                logger.info("send_thread has been interrupted");
                rece_thread.interrupt();
                logger.info("rece_thread has been interrupted");
                break;
            }
            Function.sleep(500);
        }
    }

    /*
    启动首发的服务
     */
    public void setup_io(){
        rece_thread=new Thread(new Runnable() {
            @Override
            public void run() {
                service_rece();
            }
        });
        send_thread=new Thread(new Runnable() {
            @Override
            public void run() {
                service_send();
            }
        });
        rece_thread.start();
        logger.info("rece_thread has been started");
        send_thread.start();
        logger.info("send_thread has been started");
    }

    /*
    启动看门狗服务
     */
    public void setup_watd(){
        watd_thread=new Thread(new Runnable() {
            @Override
            public void run() {
                service_watd();
            }
        });
        watd_thread.start();
        logger.info("watd_thread has been started");
    }

    /*
    处理消息函数
     */
    public void dispose_message(){
        logger.info("start to dispose message");
        while(user_checkpoint_main){
            if(user_checkpoint_rece){
                logger.info("check message");
                if(message_rece.equals(null)){
                    logger.warning("rece_message equals null");
                    user_checkpoint_main=false;
                }
                Function.dispose(message_rece);
                message_rece="0";
                user_checkpoint_rece=false;
                logger.info("user_checkpoint_rece has been turned to： "+user_checkpoint_rece);
            }else{
                //logger.info("rece not true");
            }
            Function.sleep(check_time_disp);
        }

        Thread.interrupted();
    }

    /*
    供外部调用的发送函数
     */
    public boolean send(String message){
        if(!user_checkpoint_send){
            message_send=message;
            logger.info("add message successed");
            return true;
        }else{
            logger.info("add message failed");
            return false;
        }
    }

}
