package de.gandev.modjn.entity.func;

import de.gandev.modjn.entity.ModbusFunction;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 *
 * @author ares
 */
public class WriteSingleCoil extends ModbusFunction {

    // raw vars
    private int outputAddress;
    private int outputValue;
    // plain var
    private boolean state;

    /*
     * Constructor for Response
     */
    public WriteSingleCoil() {
        super(WRITE_SINGLE_COIL);
    }

    /*
     * Constructor for Request
     */
    public WriteSingleCoil(int outputAddress, boolean state) {
        super(WRITE_SINGLE_COIL);

        int value = state ? 0xFF00 : 0x0000;

        this.outputAddress = outputAddress;
        this.outputValue = value;
        this.state = state;
    }

    public int getOutputAddress() {
        return outputAddress;
    }

    public boolean isState() {
        return state;
    }

    @Override
    public int calculateLength() {
        //Function Code + Output Address + Output Value
        return 1 + 2 + 2;
    }

    @Override
    public void decode(ByteBuf data) {
        outputAddress = data.readUnsignedShort();
        outputValue = data.readUnsignedShort();

        state = outputValue == 0xFF00;
    }

    @Override
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer(calculateLength());
        buf.writeByte(getFunctionCode());
        buf.writeShort(outputAddress);
        buf.writeShort(outputValue);

        return buf;
    }

    @Override
    public String toString() {
        return "WriteSingleCoil{" + "outputAddress=" + outputAddress + ", state=" + state + '}';
    }
}
