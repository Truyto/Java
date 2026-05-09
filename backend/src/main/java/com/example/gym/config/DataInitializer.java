
package com.example.gym.config;

import com.example.gym.entity.Action;
import com.example.gym.entity.Science;
import com.example.gym.entity.User;
import com.example.gym.repository.ActionRepository;
import com.example.gym.repository.ScienceRepository;
import com.example.gym.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final ScienceRepository scienceRepository;
    private final ActionRepository actionRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) {
        try {
            // 初始化用户数据（包含多种健身初衷）
            if (userRepository.findAll().isEmpty()) {
                // 用户1 - Lv.10 健身之神
                User user1 = new User();
                user1.setUsername("admin");
                user1.setPassword(passwordEncoder.encode("123456"));
                user1.setMotto("自律给我自由");
                user1.setFitReason("想拥有健康的身体和完美的身材");
                user1.setLevel(10);
                user1.setExp(950);
                userRepository.save(user1);
                
                // 用户2 - Lv.5 健身教练
                User user2 = new User();
                user2.setUsername("test");
                user2.setPassword(passwordEncoder.encode("123456"));
                user2.setMotto("坚持就是胜利");
                user2.setFitReason("为了变得更自信");
                user2.setLevel(5);
                user2.setExp(420);
                userRepository.save(user2);
                
                // 用户3 - Lv.3 健身爱好者
                User user3 = new User();
                user3.setUsername("fitness1");
                user3.setPassword(passwordEncoder.encode("123456"));
                user3.setMotto("运动让我快乐");
                user3.setFitReason("想瘦下来穿好看的衣服");
                user3.setLevel(3);
                user3.setExp(280);
                userRepository.save(user3);
                
                // 用户4 - Lv.7 健身宗师
                User user4 = new User();
                user4.setUsername("fitness2");
                user4.setPassword(passwordEncoder.encode("123456"));
                user4.setMotto("每天进步一点点");
                user4.setFitReason("喜欢运动后的成就感");
                user4.setLevel(7);
                user4.setExp(680);
                userRepository.save(user4);
                
                // 用户5 - Lv.2 健身新手
                User user5 = new User();
                user5.setUsername("fitness3");
                user5.setPassword(passwordEncoder.encode("123456"));
                user5.setMotto("健康是最好的投资");
                user5.setFitReason("为了身体健康");
                user5.setLevel(2);
                user5.setExp(150);
                userRepository.save(user5);
                
                // 用户6 - Lv.6 金牌教练
                User user6 = new User();
                user6.setUsername("fitness4");
                user6.setPassword(passwordEncoder.encode("123456"));
                user6.setMotto("超越自我");
                user6.setFitReason("想练出好看的线条");
                user6.setLevel(6);
                user6.setExp(550);
                userRepository.save(user6);
                
                // 用户7 - Lv.4 健身达人
                User user7 = new User();
                user7.setUsername("fitness5");
                user7.setPassword(passwordEncoder.encode("123456"));
                user7.setMotto("不逼自己一把不知道多优秀");
                user7.setFitReason("想挑战自己");
                user7.setLevel(4);
                user7.setExp(360);
                userRepository.save(user7);
                
                // 用户8 - Lv.1 健身小白
                User user8 = new User();
                user8.setUsername("fitness6");
                user8.setPassword(passwordEncoder.encode("123456"));
                user8.setMotto("爱自己是终身浪漫的开始");
                user8.setFitReason("想变得更自信");
                user8.setLevel(1);
                user8.setExp(80);
                userRepository.save(user8);
                
                // 用户9 - Lv.8 健身传奇
                User user9 = new User();
                user9.setUsername("fitness7");
                user9.setPassword(passwordEncoder.encode("123456"));
                user9.setMotto("生命在于运动");
                user9.setFitReason("喜欢流汗的感觉");
                user9.setLevel(8);
                user9.setExp(780);
                userRepository.save(user9);
                
                // 用户10 - Lv.9 健身之神
                User user10 = new User();
                user10.setUsername("fitness8");
                user10.setPassword(passwordEncoder.encode("123456"));
                user10.setMotto("陪伴是最长情的告白");
                user10.setFitReason("为了陪伴家人更久");
                user10.setLevel(9);
                user10.setExp(880);
                userRepository.save(user10);
                
                // 用户11 - Lv.3 健身爱好者
                User user11 = new User();
                user11.setUsername("fitness9");
                user11.setPassword(passwordEncoder.encode("123456"));
                user11.setMotto("做自己的英雄");
                user11.setFitReason("想变强壮保护爱的人");
                user11.setLevel(3);
                user11.setExp(220);
                userRepository.save(user11);
                
                // 用户12 - Lv.5 健身教练
                User user12 = new User();
                user12.setUsername("fitness10");
                user12.setPassword(passwordEncoder.encode("123456"));
                user12.setMotto("告别emo拥抱阳光");
                user12.setFitReason("为了摆脱emo情绪");
                user12.setLevel(5);
                user12.setExp(480);
                userRepository.save(user12);
                
                // 用户13 - Lv.2 健身新手
                User user13 = new User();
                user13.setUsername("fitness11");
                user13.setPassword(passwordEncoder.encode("123456"));
                user13.setMotto("自律即自由");
                user13.setFitReason("想体验自律的快乐");
                user13.setLevel(2);
                user13.setExp(120);
                userRepository.save(user13);
                
                // 用户14 - Lv.4 健身达人
                User user14 = new User();
                user14.setUsername("fitness12");
                user14.setPassword(passwordEncoder.encode("123456"));
                user14.setMotto("掌控自己的人生");
                user14.setFitReason("想让生活更有掌控感");
                user14.setLevel(4);
                user14.setExp(320);
                userRepository.save(user14);
                
                // 用户15 - Lv.6 金牌教练
                User user15 = new User();
                user15.setUsername("fitness13");
                user15.setPassword(passwordEncoder.encode("123456"));
                user15.setMotto("全新的自己");
                user15.setFitReason("想和过去的自己告别");
                user15.setLevel(6);
                user15.setExp(590);
                userRepository.save(user15);
                
                // 用户16 - Lv.1 健身小白
                User user16 = new User();
                user16.setUsername("fitness14");
                user16.setPassword(passwordEncoder.encode("123456"));
                user16.setMotto("阳光总在风雨后");
                user16.setFitReason("想拥有更好的精神状态");
                user16.setLevel(1);
                user16.setExp(50);
                userRepository.save(user16);
                
                // 用户17 - Lv.7 健身宗师
                User user17 = new User();
                user17.setUsername("fitness15");
                user17.setPassword(passwordEncoder.encode("123456"));
                user17.setMotto("蜕变从现在开始");
                user17.setFitReason("想穿下S码的衣服");
                user17.setLevel(7);
                user17.setExp(720);
                userRepository.save(user17);
                
                // 用户18 - Lv.8 健身传奇
                User user18 = new User();
                user18.setUsername("fitness16");
                user18.setPassword(passwordEncoder.encode("123456"));
                user18.setMotto("健康第一");
                user18.setFitReason("体检报告各项指标正常");
                user18.setLevel(8);
                user18.setExp(820);
                userRepository.save(user18);
                
                // 用户19 - Lv.3 健身爱好者
                User user19 = new User();
                user19.setUsername("fitness17");
                user19.setPassword(passwordEncoder.encode("123456"));
                user19.setMotto("遇见更好的自己");
                user19.setFitReason("参加同学聚会惊艳所有人");
                user19.setLevel(3);
                user19.setExp(260);
                userRepository.save(user19);
                
                // 用户20 - Lv.4 健身达人
                User user20 = new User();
                user20.setUsername("fitness18");
                user20.setPassword(passwordEncoder.encode("123456"));
                user20.setMotto("坚持就是胜利");
                user20.setFitReason("穿婚纱的时候美美的");
                user20.setLevel(4);
                user20.setExp(380);
                userRepository.save(user20);
            }
        } catch (Exception e) {
            System.out.println("User table not ready yet, skipping initialization");
        }
        
        try {
            // 初始化科普数据
            if (scienceRepository.findAll().isEmpty()) {
                Science s1 = new Science();
                s1.setTitle("吃不胖的瘦子该如何增肌？");
                s1.setDescription("针对偏瘦体质的增肌饮食、训练计划，科学打破「外胚型」刻板印象");
                s1.setCategory("增肌科普");
                s1.setReadTime(5);
                s1.setImgUrl("/images/science/bg1.jpg");
                scienceRepository.save(s1);
                
                Science s2 = new Science();
                s2.setTitle("胖子如何无痛减肥？");
                s2.setDescription("低门槛减脂方法，不用节食、不用高强度运动，轻松养成易瘦体质");
                s2.setCategory("减脂科普");
                s2.setReadTime(7);
                s2.setImgUrl("/images/science/dragon.png");
                scienceRepository.save(s2);
                
                Science s3 = new Science();
                s3.setTitle("维持身材的饮食指南");
                s3.setDescription("不用计算卡路里，普通人也能坚持的健康饮食原则");
                s3.setCategory("饮食科普");
                s3.setReadTime(4);
                s3.setImgUrl("/images/science/helloworld.jpeg");
                scienceRepository.save(s3);
                
                Science s4 = new Science();
                s4.setTitle("运动后拉伸能防腿粗吗？");
                s4.setDescription("辟谣！拉伸对肌肉形态的真实影响，正确拉伸方法教学");
                s4.setCategory("训练科普");
                s4.setReadTime(3);
                s4.setImgUrl("/images/science/now.png");
                scienceRepository.save(s4);
                
                Science s5 = new Science();
                s5.setTitle("空腹运动燃脂效果更好？");
                s5.setDescription("空腹vs餐后运动的科学对比，不同人群的最佳运动时间");
                s5.setCategory("减脂科普");
                s5.setReadTime(6);
                s5.setImgUrl("/images/science/qrcode.jpg");
                scienceRepository.save(s5);
            }
        } catch (Exception e) {
            System.out.println("Science table not ready yet, skipping initialization");
        }
        
        try {
            // 初始化动作数据
            if (actionRepository.findAll().isEmpty()) {
                Action a1 = new Action();
                a1.setName("徒手深蹲");
                a1.setPart("腿部");
                a1.setLevel("入门");
                a1.setContent("锻炼臀腿，注意膝盖不超过脚尖，保持背部挺直");
                a1.setImgUrl("/images/action/bg1.jpg");
                actionRepository.save(a1);
                
                Action a2 = new Action();
                a2.setName("平板支撑");
                a2.setPart("核心");
                a2.setLevel("入门");
                a2.setContent("核心训练，保持身体成一条直线，收紧核心");
                a2.setImgUrl("/images/action/dragon.png");
                actionRepository.save(a2);
                
                Action a3 = new Action();
                a3.setName("俯卧撑");
                a3.setPart("胸部");
                a3.setLevel("入门");
                a3.setContent("胸肌+手臂训练，新手可跪姿做，保持身体稳定");
                a3.setImgUrl("/images/action/helloworld.jpeg");
                actionRepository.save(a3);
                
                Action a4 = new Action();
                a4.setName("开合跳");
                a4.setPart("全身");
                a4.setLevel("入门");
                a4.setContent("热身必备，提升心率，燃脂效率高");
                a4.setImgUrl("/images/action/now.png");
                actionRepository.save(a4);
                
                Action a5 = new Action();
                a5.setName("弓步蹲");
                a5.setPart("腿部");
                a5.setLevel("进阶");
                a5.setContent("单侧臀腿训练，改善左右腿不平衡");
                a5.setImgUrl("/images/action/qrcode.jpg");
                actionRepository.save(a5);
                
                Action a6 = new Action();
                a6.setName("卷腹");
                a6.setPart("核心");
                a6.setLevel("入门");
                a6.setContent("腹部训练，避免用脖子发力，保持呼吸节奏");
                a6.setImgUrl("/images/action/definemoment.png");
                actionRepository.save(a6);
                
                Action a7 = new Action();
                a7.setName("臀桥");
                a7.setPart("臀部");
                a7.setLevel("入门");
                a7.setContent("激活臀部，改善假胯宽，注意顶峰收缩");
                a7.setImgUrl("/images/action/exit.png");
                actionRepository.save(a7);
                
                Action a8 = new Action();
                a8.setName("高抬腿");
                a8.setPart("腿部");
                a8.setLevel("入门");
                a8.setContent("强化核心+燃脂，保持上半身稳定");
                a8.setImgUrl("/images/action/bg1.jpg");
                actionRepository.save(a8);
            }
        } catch (Exception e) {
            System.out.println("Action table not ready yet, skipping initialization");
        }
    }
}
