package com.example.sharding.domain.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author wf
 * @date 2019/11/14
 */

public class CsOrderTableShardingAlgorithm implements PreciseShardingAlgorithm<Long> {


    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {

        for (String tableName : availableTargetNames) {

            if (tableName.endsWith(shardingValue.getValue()+"")) {
                return tableName;
            }
        }

        throw new IllegalArgumentException();
    }
}
