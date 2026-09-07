package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();   //创建一个空的Map，用于存储jwt令牌的payload部分
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId()); //将员工的id存储到payload部分（key为empId，value为员工的id）
        String token = JwtUtil.createJWT(               //生成token
                jwtProperties.getAdminSecretKey(),      //管理端员工生成jwt令牌的密钥
                jwtProperties.getAdminTtl(),            //管理端员工生成jwt令牌的过期时间
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder() //创建一个EmployeeLoginVO对象，用于存储员工登录返回的数据（返回给前端使用）
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    //新增员工
    @PostMapping
    @ApiOperation("新增员工")
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工{}", employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }

    //分页查询员工列表
    @GetMapping("/page")
    @ApiOperation("分页查询员工列表")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("分页查询员工列表{}", dishPageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /*
    * 启用/禁用员工
    * @PathVariable 将路径中的status参数绑定到方法的参数上
    */
    @PostMapping("/status/{status}")
    @ApiOperation("启用/禁用员工")
    public Result startOrStop(@PathVariable Integer status, Long id ) {
        log.info("启用/禁用员工：{}，员工id：{}", status, id);
        employeeService.startOrStop(status, id);
        return Result.success();
    }

    //根据id查询员工信息
    @GetMapping("/{id}")
    @ApiOperation("根据id查询员工信息")
    public Result<Employee> getById(@PathVariable Long id) {
        log.info("根据id查询员工信息：{}", id);
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    //修改员工信息
    @PutMapping
    @ApiOperation("修改员工信息")
    public Result update(@RequestBody EmployeeDTO employeeDTO) {
        log.info("修改员工信息{}", employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }
}
