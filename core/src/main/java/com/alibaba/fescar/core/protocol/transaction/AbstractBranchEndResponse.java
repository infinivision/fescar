/*
 *  Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.alibaba.fescar.core.protocol.transaction;

import com.alibaba.fescar.core.model.BranchStatus;
import com.alibaba.fescar.core.protocol.CodecHelper;
import com.alibaba.fescar.core.protocol.ResultCode;
import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

public abstract class AbstractBranchEndResponse extends AbstractTransactionResponse {
    protected BranchStatus branchStatus;
    // add by infinivision
    protected String xid;
    protected long branchId;
    // add by infinivision

    public BranchStatus getBranchStatus() {
        return branchStatus;
    }

    public void setBranchStatus(BranchStatus branchStatus) {
        this.branchStatus = branchStatus;
    }

    @Override
    protected void doEncode() {
        super.doEncode();
        CodecHelper.write(byteBuffer, xid);
        CodecHelper.write(byteBuffer, branchId);
        if (null != branchStatus) {
            byteBuffer.put((byte) branchStatus.ordinal());
        }
    }

    @Override
    public void decode(ByteBuffer byteBuffer) {
        super.decode(byteBuffer);
        setXid(CodecHelper.readString(byteBuffer));
        setBranchId(CodecHelper.readLong(byteBuffer));
        if (getResultCode() == ResultCode.Success) {
            branchStatus = BranchStatus.get(byteBuffer.get());
        }
    }

    @Override
    public boolean decode(ByteBuf in) {
        if (!super.decode(in)) {
            return false;
        }

        setXid(CodecHelper.readString(in));
        setBranchId(CodecHelper.readLong(in));
        if (getResultCode() == ResultCode.Success) {
            branchStatus = BranchStatus.get(in.readByte());
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Xid=");
        result.append(xid);
        result.append("branchId=");
        result.append(branchId);
        result.append("branchStatus=");
        result.append(branchStatus);
        result.append(",");
        result.append("result code =");
        result.append(getResultCode());
        result.append(",");
        result.append("getMsg =");
        result.append(getMsg());

        return result.toString();
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public long getBranchId() {
        return branchId;
    }

    public void setBranchId(long branchId) {
        this.branchId = branchId;
    }
}
