import java.io.IOException;

public class Function {

    public static void sleep(long ms){
        try{
            Thread.sleep(ms);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void exec(String m_message){
        try {
            Runtime.getRuntime().exec(m_message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void shutdown(){
        exec("sudo shutdown now");
    }
    public static void reboot(){
        exec("sudo reboot");
    }

    public static void dispose(String m_message){
        if(m_message.equals("shutdown")){
            shutdown();
        }else if(m_message.equals("reboot")){
            reboot();
        }

    }
}
