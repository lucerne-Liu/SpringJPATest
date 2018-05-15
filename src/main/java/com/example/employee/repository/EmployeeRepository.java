package com.example.employee.repository;

import com.example.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    //以下所有的*都代表变量

    //1.查询名字是*的第一个employee
    Employee findTopByName(String name);
    //方法二
    Employee queryFirstByName(String name);
    //方法三
    Employee findFirstByName(String name);

    //2.找出Employee表中第一个姓名包含`*`字符并且薪资大于*的雇员个人信息
    Employee findFirstByNameContainingAndSalaryGreaterThan(String nameContains,Integer salary);
    //方法二：测试自定义SQL查询
    @Query(value = "select * from Employee t where t.name like %?1% and t.salary > ?2 limit 1", nativeQuery = true)
    Employee findUserByNameCharacterAndSalary(String nameContains,Integer salary);

    //3.找出一个薪资最高且公司ID是*的雇员以及该雇员的姓名
    @Query(value = "SELECT * FROM Employee t WHERE t.companyId =:companyId ORDER BY salary DESC limit 1", nativeQuery = true)
    Employee findMaxSalaryByCompanyId(@Param("companyId") Integer companyId);

    //4.实现对Employee的分页查询，每页两个数据
    Page<Employee> findAll(Pageable pageable);

    //5.查找**的所在的公司的公司名称
    @Query("select b.companyName from Employee a, Company b where a.companyId = b.id and a.name = ?1")
    String findCompanyNameByEmployeeName(String name);

    //6.将*的名字改成*,输出这次修改影响的行数
    @Modifying
    @Query("update Employee u set u.name = ?1 where u.name = ?2")
    int modifyByIName(String newName, String name);

    //7.删除姓名是*的employee
    @Modifying
    @Query("delete from Employee where name = ?1")
    void deleteByName(String name);

    int removeByName(String name);

//    Employee save(Employee employee);

    @Query(value = "select ",nativeQuery = true )
    List<Employee> findEmployeesByCompanyName(String companyName);
}
