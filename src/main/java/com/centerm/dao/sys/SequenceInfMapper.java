package com.centerm.dao.sys;

import com.centerm.model.sys.SequenceInf;

public interface SequenceInfMapper {
    int deleteByPrimaryKey(String name);

    int insert(SequenceInf record);

    int insertSelective(SequenceInf record);

    SequenceInf selectByPrimaryKey(String name);

    int updateByPrimaryKeySelective(SequenceInf record);

    int updateByPrimaryKey(SequenceInf record);
    
    int getPseq();
    
    int resetPseq();
    
    Integer getMchntCd();
    
    Integer getTermCd();
    
    Integer getMenutpId();
    
    Integer getProductId();

}