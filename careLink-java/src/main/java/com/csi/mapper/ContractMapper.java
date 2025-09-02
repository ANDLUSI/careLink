package com.csi.mapper;

import com.csi.domain.Contract;

import java.util.List;
import java.util.Map;

public interface ContractMapper {

    //老人入住同时新增老人id
    int saveId(int residentId);

    //添加合同信息
    int addContract(Contract contract);

    //删除合同
    int deleteContractById(int id);

    //修改合同
    int modifyContract(Contract contract);

    //通过合同id查询合同
    Contract findContractById(int id);

    //查询全部合同+通过老人id查询+通过合同编号查询
    List<Contract> findAllContract(String key);
}
