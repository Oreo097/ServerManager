import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Core {
    public ExecutorService core_threadpool;//线程池

    public int thread_number=10;//线程池线程数
    public int core_threadnumber_now=0;//现行线程数

    public int core_portnumber=2455;//核心端口号

    public boolean core_checkpoint_main;//控制核心开关的checkpoint

    public User user[]=null;//id池
    public int user_id_pointer=-1;//新的id池的指针

    public int id_recycle[];//回收的id，优先使用回收的id，且有限使用最后一个，当回收的id池为空的时候才使用新的id池
    public int id_recycle_pointer=-1;

    public int distribute_time;

    public MessagePool user_messaage_pool[];//用户的消息池索引，其索引值和user_id值对应

    private ServerSocket coer_serversocket;


    /*
    构造函数
     */
    public Core(int port_number,int thread_number){
        this.core_portnumber=port_number;
        this.thread_number=thread_number;
    }
    /*
    重载构造函数
     */
    public Core(){

    }

    /*
        log
     */
    Logger logger= Logger.getLogger(Core.class.getName());

    /*
    创建服务器端口
     */
    public void setup_serversocket(){
        try{
            coer_serversocket=new ServerSocket(core_portnumber);
            logger.info("setup ServerSocket at: "+core_portnumber);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
    创建线程池
     */
    public void setup_threadpool(){
        try{
            core_threadpool = Executors.newCachedThreadPool();
            logger.info("cached thread pool has been already seted up!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /*
    创建Socket
     */
    public Socket setup_socket(){
        Socket socket=null;
        try{
            logger.info("waitting for connection");
            socket=coer_serversocket.accept();
            return socket;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /*
    向线程池中添加线程
     */
    public void add_thread(Thread m_thread){
        core_threadpool.submit(m_thread);
        core_threadnumber_now++;
    }

    public void service_distribute_message(){
        int id,address,rece_pointer;
        while(core_checkpoint_main){
            for(int i=0;i<=user_id_pointer;i++){
                if(user[i]==null){
                    continue;
                }
                synchronized (user[i].user_messagepool){
                    rece_pointer=user[i].user_messagepool.message_rece_pointer;
                    if(rece_pointer>=0){
                        for(int t=rece_pointer;t>-1;t++){
                            if(user[i]!=null){//检测是否为空值
                                address=user[i].user_messagepool.messages_rece[rece_pointer].address;
                                synchronized (user[address].user_messagepool){
                                user[address].user_messagepool.message_send_pointer++;
                                user[i].user_messagepool.messages_rece[rece_pointer]=user[address].user_messagepool.messages_send[user[address].user_messagepool.message_send_pointer];
                                user[i].user_messagepool.messages_rece[rece_pointer]=null;
                                user[i].user_messagepool.message_rece_pointer--;
                            }
                            }
                        }
                    }
                }
            }
            Function.sleep(distribute_time);
        }
    }

    public void service_distribute_thread(){
        while(core_checkpoint_main){
            if(core_threadnumber_now<thread_number){
                Thread user_thread=new Thread(new Runnable() {
                    int user_id;
                    @Override
                    public void run() {
                        

                        synchronized (this){
                            user_id_pointer++;
                            user_id=user_id_pointer;
                        }
                        user_messaage_pool[user_id]=new MessagePool();
                        user[user_id]=new User(user_id,setup_socket(),user_messaage_pool[user_id]);
                        user[user_id].init_io();
                        user[user_id].setup_io();//开启收发线程
                        user[user_id].setup_watd();//开启管理线程
                        while(core_checkpoint_main){//检测用户服务是否结束
                            if(user[user_id].user_checkpoint_main==false){
                                user[user_id]=null;
                                if(user_id!=user_id_pointer){
                                    synchronized (this){
                                        id_recycle_pointer++;
                                        id_recycle[id_recycle_pointer]=user_id;
                                    }
                                    user[user_id]=null;
                                    break;
                                }
                            }
                        }
                    }
                });
                add_thread(user_thread);
            }
        }
    }

    public void main_service(){
        setup_serversocket();
        setup_threadpool();

    }


}
