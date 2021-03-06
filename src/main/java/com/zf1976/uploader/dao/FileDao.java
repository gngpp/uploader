package com.zf1976.uploader.dao;

import com.zf1976.uploader.model.File;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @author mac
 */
@Mapper
public interface FileDao {

    /**
     * 分页查询
     *
     * @param pageNumber 页码
     * @param pageSize 页大小
     * @return file list
     */
    Collection<File> selectPages(@Param("pageNumber") Integer pageNumber,@Param("pageSize") Integer pageSize);

    /**
     * 查上传文件总数
     *
     * @return records
     */
    Integer selectTotalRecord();

    /**
     * 查询所有文件数据
     *
     * @return file list
     */
    Collection<File> selectAll();
    /**
     * 通过主键获取一行数据
     * @param id 文件id
     * @return /
     */
    File getById(Long id);

    /**
     * 插入一行数据
     * @param file
     * @return
     */
    int save(File file);

    /**
     * 更新一行数据
     * @param file
     * @return
     */
    int update(File file);

    /**
     * 删除一行数据
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 根据一个或多个属性获取File
     * @param file
     * @return
     */
    File getByFile(File file);
}
