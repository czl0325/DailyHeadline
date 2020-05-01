package com.heima.behavior.test;

import com.heima.behavior.BehaviorJarApplication;
import com.heima.behavior.service.AppShowBehaviorService;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.behavior.dtos.ShowBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.threadlocal.AppThreadLocalUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = BehaviorJarApplication.class)
@RunWith(SpringRunner.class)
public class BehaviorTest {
    @Autowired
    private AppShowBehaviorService appShowBehaviorService;


    @Test
    public void saveTest() {
        ApUser user = new ApUser();
        user.setId(1L);
        AppThreadLocalUtils.setUser(user);
        List<ApArticle> articles = new ArrayList<>();
        ApArticle article = new ApArticle();
        article.setId(200);
        articles.add(article);
        ShowBehaviorDto dto = new ShowBehaviorDto();
        dto.setArticleIds(articles);
        ResponseResult responseResult = appShowBehaviorService.saveShowBehavior(dto);
        System.out.println("輸出=" + responseResult.getCode());
    }
}
