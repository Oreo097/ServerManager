import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Core {
    public ExecutorService core_threadpool;//线程池

    public int thread_number=10;//线程池线程数
    public int core_threadnumber_now=0;

    public int core_portnumber=2455;//核心端口号

    public boolean core_checkpoint_main;


    private ServerSocket coer_serversocket;




    public Core(){

    }

    Logger logger= Logger.getLogger(Core.class.getName());

    public void setup_serversocket(){
        try{
            coer_serversocket=new ServerSocket(core_portnumber);
            logger.info("setup ServerSocket at: "+core_portnumber);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setup_threadpool(){
        try{
            core_threadpool = Executors.newCachedThreadPool();
            logger.info("cached thread pool has been already seted up!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

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
    public void add_thread(Thread m_thread){
        core_threadpool.submit(m_thread);
        core_threadnumber_now++;
    }


}
