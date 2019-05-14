package cn.csg.jobschedule.controller;

import cn.csg.common.ResultData;
import cn.csg.common.ResultStatus;
import cn.csg.common.enums.ExplodeType;
import cn.csg.common.enums.RuleKeyEnum;
import cn.csg.common.enums.StatusEnum;
import cn.csg.common.vo.ExplodeVo;
import cn.csg.common.vo.RuleVo;
import cn.csg.jobschedule.service.ExplodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/jobs/params")
public class JobsParamsController {
    @Autowired
    private ExplodeService explodeService;

    @GetMapping(value = "/list")
    public ResultData<List<ExplodeVo>> getAllExplodes(){
        return new ResultData<>(
                explodeService.findAll(),
                ResultStatus.initStatus(StatusEnum.SUCCESS)
        );
    }

    @PostMapping(value = "/save")
    public ResultData<ExplodeVo> save(
            @RequestBody ExplodeVo explodeVo
    ){
        explodeService.save(explodeVo);
        System.out.println(explodeVo);
        return new ResultData<>(
                null,
                ResultStatus.initStatus(StatusEnum.UPDATE)
        );
    }

    @PostMapping(value = "/init")
    public ResultData<ExplodeVo> init(
            String name,
            Integer window,
            Integer target
    ){
        for(String key : ExplodeType.EXPLODE_MAP.keySet()) {
            explodeService.save(
                    new ExplodeVo(
                            ExplodeType.EXPLODE_MAP.get(key).getName(),
                            Arrays.asList(
                                    RuleVo.put(RuleKeyEnum.WINDOWN.getKey(), BigDecimal.valueOf(window)),
                                    RuleVo.put(RuleKeyEnum.TARGET.getKey(), BigDecimal.valueOf(target))
                            )
                    )
            );
        }

        return new ResultData<>(
                explodeService.save(
                        new ExplodeVo(
                            name,
                            Arrays.asList(
                                RuleVo.put(RuleKeyEnum.WINDOWN.getKey(), BigDecimal.valueOf(window)),
                                RuleVo.put(RuleKeyEnum.TARGET.getKey(),BigDecimal.valueOf(target))
                            )
                        )
                ),
                ResultStatus.initStatus(StatusEnum.SUCCESS)
        );
    }
}
