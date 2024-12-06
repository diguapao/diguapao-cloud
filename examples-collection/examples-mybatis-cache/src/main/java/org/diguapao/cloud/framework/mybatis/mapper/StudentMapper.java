package org.diguapao.cloud.framework.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import org.diguapao.cloud.framework.mybatis.entity.StudentEntity;

public interface StudentMapper {

    public StudentEntity getStudentById(int id);

    public int addStudent(StudentEntity student);

    public int updateStudentName(@Param("name") String name, @Param("id") int id);

    public StudentEntity getStudentByIdWithClassInfo(int id);

}
