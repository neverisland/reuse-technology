package cn.yang.authentication.authorization.user.repository;

import cn.yang.authentication.authorization.user.dal.PermissionDo;
import cn.yang.authentication.authorization.user.entity.Permission;
import cn.yang.authentication.authorization.user.enums.PermissionEnum;
import cn.yang.authentication.authorization.user.mapper.PermissionMapper;
import cn.yang.common.data.structure.utils.bean.BeanConvertUtils;
import cn.yang.foundational.capability.id.generator.IdGenerator;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 权限 仓储层
 *
 * @author 未见清海
 */
@Component
public class PermissionRepository {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private IdGenerator idGenerator;

    /**
     * 获取所有的权限列表
     *
     * @return 所有权限列表
     */
    @Cacheable(cacheNames = "permission-all")
    public List<Permission> selectAllData() {
        List<PermissionDo> permissionDoList = permissionMapper.selectAllData();
        return BeanConvertUtils.convert(permissionDoList, Permission.class);
    }

    /**
     * 批量新增权限
     *
     * @param addDataList 新增的权限枚举
     */
    @CacheEvict(cacheNames = {"permission-all", "permission-ids", "permission-marks"})
    public void batchInsertData(List<PermissionEnum> addDataList) {
        List<PermissionDo> permissionDoList = BeanConvertUtils.convert(addDataList, PermissionDo.class);
        LocalDateTime now = LocalDateTime.now();
        permissionDoList.forEach(permissionDo -> {
            permissionDo.setId(idGenerator.getId());
            permissionDo.setCreateTime(now);
            permissionDo.setUpdateTime(now);
        });
        permissionMapper.insertBatch(permissionDoList);
    }

    /**
     * 批量修改权限数据
     *
     * @param updateDataList 修改权限数据
     */
    @CacheEvict(cacheNames = {"permission-all", "permission-ids", "permission-marks"})
    public void batchUpdateDate(List<Permission> updateDataList) {
        List<PermissionDo> permissionDoList = BeanConvertUtils.convert(updateDataList, PermissionDo.class);
        LocalDateTime now = LocalDateTime.now();
        permissionDoList.forEach(permissionDo -> {
            permissionDo.setUpdateTime(now);
        });
        permissionMapper.batchUpdateDate(permissionDoList);
    }

    /**
     * 根据权限id列表删除权限
     *
     * @param deleteIdList 删除权限id列表
     */
    @CacheEvict(cacheNames = {"permission-all", "permission-ids", "permission-marks"})
    public void deleteByIdList(List<String> deleteIdList) {
        permissionMapper.deleteByIdList(deleteIdList);
    }

    /**
     * 根据权限标识列表获取权限数据
     *
     * @param permissionMarkList 权限标识列表
     * @return 权限列表数据
     */
    @Cacheable(cacheNames = "permission-marks", key = "#permissionMarkList")
    public List<Permission> selectDataByMarks(List<String> permissionMarkList) {
        List<PermissionDo> permissionDoList = permissionMapper.selectDataByMarks(permissionMarkList);
        return BeanConvertUtils.convert(permissionDoList, Permission.class);
    }

    /**
     * 根据权限id列表获取权限数据
     *
     * @param permissionIdList 权限id列表
     * @return 权限列表数据
     */
    @Cacheable(cacheNames = "permission-ids", key = "#permissionIdList")
    public List<Permission> selectDataByIdList(List<String> permissionIdList) {
        List<PermissionDo> permissionDoList = permissionMapper.selectDataByIdList(permissionIdList);
        return BeanConvertUtils.convert(permissionDoList, Permission.class);
    }
}




