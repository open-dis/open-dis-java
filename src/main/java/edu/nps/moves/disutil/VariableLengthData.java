
package edu.nps.moves.disutil;

import java.io.*;

/**
 * DIS has a frequent problem of shifting between units when describing
 * the data length of some fields. This is an attempt to fix that once,
 * in hand-written code, rather than generate source code to do it.<p>
 * 
 * The field has a length, a units descriptor (bytes or bits) and a descriptor
 * that tells us whether the record as a whole should end on a 8, 16, 32, or
 * 64 bit word boundary, including the length field. 
 * 
 * @author DMcG
 */
public class VariableLengthData 
{
    
    /** What units does the length field use? Typically bytes (octet)
     * but also often bits. bits, bytes, 16 bit words, 32 bit words,
     * 64 bit words.
     */
    public enum DataLengthUnits { L_BIT, L_OCTET };
    
    /** We want the record as a whole to end on a byte, 16 bit word, 32 bit
     *  word, or 64 bit word boundary, so we pad to reach that.
     */
    public enum PadToBoundary { BOUNDARY_8, BOUNDARY_16, BOUNDARY_32, BOUNDARY_64};
    
    public long dataLength = 0;
    public VariableLengthData.PadToBoundary padToBoundary = PadToBoundary.BOUNDARY_64;
    public VariableLengthData.DataLengthUnits dataLengthUnits = DataLengthUnits.L_BIT;
    public byte[] data = null;
    
    
    public VariableLengthData()
    {
        padToBoundary = VariableLengthData.PadToBoundary.BOUNDARY_64;
        dataLengthUnits = VariableLengthData.DataLengthUnits.L_BIT;        
    }
    
    public int getMarshalledSize()
    {
        int marshalSize = 0;
        marshalSize = marshalSize + 4;
        if(data != null)
        {
            marshalSize = marshalSize + data.length;
        }
        
        return marshalSize; 
    }
    
    
    public void setDataLength(int dataLength)
    {
        this.dataLength = dataLength;
    }
    
    public int getDataLength()
    {
        return (int)this.dataLength;
    }
    
    /**
     * Set the data to write. The assumption is that if your data is not
     * written to a byte boundary (eg, 17 bits) you have padded the data
     * array yourself to at least a byte boundary (eg, 24 bits or three
     * bytes in the byte array). The code will handle padding the rest
     * to a given byte, short, int, or 64 bit record boundary.
     * @param data 
     */
    public void setData(byte[] data)
    {
        this.data = data;
    }
    
    /**
     * Retrieves data, padded to at least a byte boundary
     * @return 
     */
    public byte[] getData()
    {
        return this.data;
    }

    public void marshal(DataOutputStream dos)
    {
        try
        {
            dos.writeInt((int)dataLength);
            int dataBytes = 0;
            int paddingBytes = 0;
            
            // This is how long the data field should be written, based
            // on the reported field length
            int writeLengthInBytes = 0;
            if(this.dataLengthUnits == DataLengthUnits.L_BIT)
            {
                writeLengthInBytes = (int)(dataLength / 8);
                int remainder = (int)(dataLength % 8);
                if(remainder != 0)
                {
                    writeLengthInBytes++;
                }
            }
            else
            {
               writeLengthInBytes = (int)this.dataLength;
            }

            paddingBytes = this.getPaddingBytes(writeLengthInBytes);
            
            if(data != null)
            {
                dos.write(data);
            }
            
            for(int idx = 0; idx < paddingBytes; idx++)
            {
                dos.writeByte(0);
            }
    
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    private int getPaddingBytes(int writeLengthInBytes)
    {
        int paddingBytes = 0;
        
        switch(padToBoundary)
        {
            case BOUNDARY_8:
                break;
            case BOUNDARY_16:
                paddingBytes = writeLengthInBytes % 2;      
                break;
            case BOUNDARY_32:
                paddingBytes = writeLengthInBytes % 4;
                break;

            case BOUNDARY_64:
                paddingBytes = writeLengthInBytes % 8;
                break;

            default:
                break;
        }
        
        return paddingBytes;
    }
   
    public void unmarshal(DataInputStream dis)
    {
        try
        {
            int dataBytesToRead = 0;
            dataLength = dis.readInt();
            if(dataLengthUnits == DataLengthUnits.L_BIT)
            {
                dataBytesToRead = (int)dataLength / 8;
                
                if(dataLength % 8 != 0)
                {
                    dataBytesToRead++;
                }
            }
            else
            {
                dataBytesToRead = (int)this.dataLength;
            }
           
            data = new byte[dataBytesToRead];
            for(int idx = 0; idx < dataBytesToRead; idx++)
            {
                data[idx] = dis.readByte();
            }
            
            // read and throw away padding bytes
            int paddingBytes = this.getPaddingBytes(data.length);
            for(int idx = 0; idx < paddingBytes; idx++)
            {
                dis.readByte();
            }
 
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }
    
public void marshal(java.nio.ByteBuffer buff)
{
    try
    {
        int paddingBytes = 0;
        
       buff.putInt( (int)dataLength);

       for(int idx = 0; idx < data.length; idx++)
       {
           buff.put((byte)data[idx]);
       } // end of array marshaling
       
       // This is how long the data field should be written, based
       // on the reported field length
       int writeLengthInBytes = 0;
       if(this.dataLengthUnits == DataLengthUnits.L_BIT)
       {
            writeLengthInBytes = (int)(dataLength / 8);
            int remainder = (int)(dataLength % 8);
            if(remainder != 0)
            {
               writeLengthInBytes++;
            }
       }
       else
       {
           writeLengthInBytes = (int)this.dataLength;
       }

        // Write padding bytes to get us on a byte/word/int/64 bit boundary
        paddingBytes = this.getPaddingBytes(writeLengthInBytes);
        for(int idx = 0; idx < paddingBytes; idx++)
        {
            buff.put((byte)0);
        }
            
    }
    catch(Exception e)
    {
        System.out.println(e);
        e.printStackTrace();
    }
}
    
    public void unmarshal(java.nio.ByteBuffer buff)
    {
       try
        {
            int dataBytesToRead = 0;
            dataLength = buff.getInt();
            if(dataLengthUnits == DataLengthUnits.L_BIT)
            {
                dataBytesToRead = (int)dataLength / 8;
                
                if(dataLength % 8 != 0)
                {
                    dataBytesToRead++;
                }
            }
           
            data = new byte[dataBytesToRead];
            for(int idx = 0; idx < dataBytesToRead; idx++)
            {
                data[idx] = buff.get();
            }
            
            // read and throw away padding bytes
            int paddingBytes = this.getPaddingBytes(data.length);
            for(int idx = 0; idx < paddingBytes; idx++)
            {
                buff.get();
            }
 
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }
  
}
