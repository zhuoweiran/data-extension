package cn.csg.jobschedule.controller;

import cn.csg.common.ResultData;
import cn.csg.common.ResultStatus;
import cn.csg.common.enums.ExplodeType;
import cn.csg.common.enums.RuleKeyEnum;
import cn.csg.common.enums.StatusEnum;
import cn.csg.common.vo.ExplodeJob;
import cn.csg.common.vo.ExplodeVo;
import cn.csg.common.vo.RuleVo;
import cn.csg.jobschedule.service.ExplodeJobService;
import cn.csg.jobschedule.service.ExplodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Api(value = "jobsParamsController",description = "爆发式通讯对")
@RestController
@RequestMapping("/jobs/params")
public class JobsParamsController {
    @Autowired
    private ExplodeService explodeService;

    @Autowired
    private ExplodeJobService explodeJobService;

    @ApiOperation(value ="查询所有爆发式通讯对",httpMethod = "GET")
    @GetMapping(value = "/list")
    public ResultData<List<ExplodeVo>> getAllExplodes(){
        return new ResultData<>(
                explodeService.findAll(),
                ResultStatus.initStatus(StatusEnum.SUCCESS)
        );
    }

    @ApiOperation(value ="保存爆发式通讯对",httpMethod = "POST")
    @PostMapping(value = "/save")
    public ResultData<ExplodeVo> save(
            @RequestBody ExplodeVo explodeVo
    ){
        ExplodeVo explodeVo1 = explodeService.findById(explodeVo.getId());
        if(explodeVo1.getValByName(RuleKeyEnum.WINDOWN.getKey()).intValue() !=
                explodeVo.getValByName(RuleKeyEnum.WINDOWN.getKey()).intValue()){
            System.out.println("修改了");
        }

        explodeService.save(explodeVo);
        return new ResultData<>(
                null,
                ResultStatus.initStatus(StatusEnum.UPDATE)
        );
    }
    @ApiOperation(value ="爆发式通讯对初始化",httpMethod = "POST")
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

    @ApiOperation(value ="爆发式通讯对任务新增",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "explodeId", dataType = "String", required = true),
            @ApiImplicitParam(name = "query", dataType = "String", required = true)
    })
    @PostMapping(value = "/save/job")
    public ResultData<ExplodeJob> saveExplodeJob(
            @RequestParam(name = "explodeId") String explodeId,
            @RequestParam(name = "query") String query
    ){
        ExplodeJob explodeJob = new ExplodeJob();
        explodeJob.setExplodeId(explodeId);
        explodeJob.setQuery(query);
       return new ResultData<>(
               explodeJobService.save(explodeJob),
               ResultStatus.initStatus(StatusEnum.SUCCESS)
       );
    }
}
