package com.heima.behavior.service.impl;

import com.heima.behavior.service.AppShowBehaviorService;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.behavior.dtos.ShowBehaviorDto;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import com.heima.model.behavior.pojos.ApShowBehavior;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.mappers.app.ApBehaviorEntryMapper;
import com.heima.model.mappers.app.ApShowBehaviorMapper;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.threadlocal.AppThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Service
@SuppressWarnings("all")
public class AppShowBehaviorServiceImpl implements AppShowBehaviorService {
    @Autowired
    private ApBehaviorEntryMapper apBehaviorEntryMapper;
    @Autowired
    private ApShowBehaviorMapper apShowBehaviorMapper;
    @Override
    public ResponseResult saveShowBehavior(ShowBehaviorDto dto) {
        ApUser user = AppThreadLocalUtils.getUser();
        if (user == null && dto.getEquipmentId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        ApBehaviorEntry apBehaviorEntry = apBehaviorEntryMapper.selectByUserIdOrEquipemntId(user!=null?user.getId():null, dto.getEquipmentId());
        if (apBehaviorEntry == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        Integer[] articleIds = new Integer[dto.getArticleIds().size()];
        for (int i=0; i<articleIds.length; i++) {
            articleIds[i] = dto.getArticleIds().get(i).getId();
        }
        List<ApShowBehavior> apShowBehaviors = apShowBehaviorMapper.selectListByEntryIdAndArticleIds(apBehaviorEntry.getId(), articleIds);
        List<Integer> ids = Arrays.asList(articleIds);
        if (!apShowBehaviors.isEmpty()) {
            apShowBehaviors.forEach(item->{
                Integer articleId = item.getArticleId();
                ids.remove(articleId);
            });
        }
        if (!ids.isEmpty()) {
            articleIds = new Integer[ids.size()];
            ids.toArray(articleIds);
            apShowBehaviorMapper.saveShowBehavior(articleIds, apBehaviorEntry.getId());
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
