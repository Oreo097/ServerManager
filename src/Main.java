import java.net.Socket;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        Core core=new Core();
        core.setup_serversocket();
        core.setup_threadpool();
        Logger logger= Logger.getLogger(Main.class.getName());
        while(true){
            if(core.core_threadnumber_now< core.thread_number) {
                core.add_thread(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Socket scoket = core.setup_socket();
                        User user = new User(scoket);
                        user.init_io();
                        user.setup_io();
                        user.setup_watd();
                        //user.setup_dispose();
                        user.dispose_message();
                        if(!user.user_checkpoint_main){
                            user=null;
                            core.core_threadnumber_now--;
                            logger.info("now threadnumber is: "+core.core_threadnumber_now);
                        }
                    }
                }));
                logger.info("add thread "+core.core_threadnumber_now);
            }
            Function.sleep(500);
        }
    }
}
