public class Function {

    public static void sleep(long ms){
        try{
            Thread.sleep(ms);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void exec(String m_message){
        if(m_message.equals("shutdown")){

        }
        if (m_message.equals("reboot")) {

        }
    }

    public static void shutdown(){
    }

}
