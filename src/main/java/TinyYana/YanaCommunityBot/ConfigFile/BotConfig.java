package TinyYana.YanaCommunityBot.ConfigFile;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BotConfig {

    private final File file; // 配置文件
    private Map<String, Object> data; // 配置数据

    public BotConfig() {
        file = new File("Config/YanaBot.yml"); // 指定文件路径和名称
        load(); // 加载配置数据
    }

    public void load() {
        try {
            if (!file.exists()) { // 如果文件不存在
                file.getParentFile().mkdirs(); // 创建父目录
                file.createNewFile(); // 创建文件
                data = new HashMap<>();
                configInit();
                save();
            }
            DumperOptions dumperOptions = new DumperOptions();
            dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            dumperOptions.setPrettyFlow(true);
            Yaml yaml = new Yaml(dumperOptions); // 创建Yaml对象
            data = yaml.load(new FileInputStream(file)); // 加载配置数据到Map中
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            Yaml yaml = new Yaml(); // 创建Yaml对象
            FileWriter writer = new FileWriter(file); // 创建FileWriter对象
            yaml.dump(data, writer); // 将配置数据转储到文件中
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object get(String key) {
        return data.get(key); // 根据键获取值
    }

    public void set(String key, Object value) {
        data.put(key, value); // 根据键设置值
    }

    private void configInit() {
        set("Token", "Token");
        set("Activity", "沙丁魚從地面裡鑽了出來");
        set("Guild_Owner_Discord_ID", 291505609289891840L);
        set("Guild_ID", 859081101859094589L);
        set("Bot_Startup_Message", "機器人已啟動 ( •̀ ω •́ )✧");

        set("Permission_Message", "你沒有權限使用此指令。");

        set("Guild_Moderator_Role_ID", 859082446406156299L);

        set("MemberCount_Enable", true);
        set("MemberCount_Channel_ID", 876124317891579914L);

        set("Welcome_Message_Enable", true);
        set("Welcome_Message_Channel_ID", 859089963181670410L);
        set("Welcome_Message_Title", ":tada: 歡迎加入彼岸花社群");
        set("Welcome_Message_Description", "這裡是一個可以讓大家聊，\\n「ACG相關話題」的社群，\\n:thumbsup: 社群推薦文作者也會不定時推薦好看的動畫作品給大家。\\n\\n:speaking_head: 當然你也可以純閒聊，\\n與這裡的大家一起閒話家常，\\n相信你可以在這邊認識更多新朋友。");
        set("Welcome_Message_Color", "YELLOW");

        set("Dynamic_Voice_Channel_Enable", true);
        set("Dynamic_Voice_Channel_ID", 889827924755222528L);

        set("Embed_Sender_No_Image", 1086553833859190804L);
        set("Embed_Sender_With_Image", 1087710314889494588L);
    }

}