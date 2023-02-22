package frc.robot.subsystems;

import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Subsystem for vision data handling
 */

//TODO: Discuss how to further complete the skeleton class

class Datagram
{
    // Datagram returns data from odroid
    Datagram(ByteBuffer byteBuffer)
    {

    }
}

/**
 * This constructor initializes the port 5800 on the roborio for reading UDP packets.
 * Disables blocking so it can be used in periodic
 */
public class SK23Vision
{
    // 
    public SK23Vision()
    {

    }

    /**
     * Receive the latest available UDP packet if any is available. Return true if a
     * packet has been read, false if none is available.
     *
     * This method discards all but the latest received packet on the socket.
     * 
     * @param sSocket
     *            A non-blocking DatagramChannel object that is used to initialize the
     *            packet receiving port on the roborio
     * 
     * @return True/False if a packet was received at the DatagramChannel on the specified
     *         port
     */

    public boolean getPacket(DatagramChannel sSocket)
    {
        return false;
    }

    public void periodic()
    {

    }

    /**
     * Gets the horizontal angle of the robot relative to the center of the hub.
     * 
     * @return The horizontal angle in degrees (CCW Positive)
     */
    public void getHorizontalAngle()
    {

    }

    /**
     * Gets the distance of the robot relative to the edge of the hub.
     * 
     * @return The distance in meters
     */
    public void getDistance()
    {

    }

}