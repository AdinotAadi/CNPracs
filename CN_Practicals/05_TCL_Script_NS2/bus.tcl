# Create a simulator object
set ns [new Simulator]
$ns color 1 Blue
$ns color 2 Red

# Open the NAM trace file
set nf [open out_bus.nam w]
$ns namtrace-all $nf

# Define a 'finish' procedure
proc finish {} {
    global ns nf
    $ns flush-trace
    close $nf
    exec nam out_bus.nam &
    exit 0
}

# Create nodes
set node1 [$ns node]
set node2 [$ns node]
set node3 [$ns node]
set node4 [$ns node]

# Create links between nodes
$ns duplex-link $node1 $node2 100Mb 1ms DropTail
$ns duplex-link $node2 $node3 100Mb 1ms DropTail
$ns duplex-link $node3 $node4 100Mb 1ms DropTail

# Create TCP connection and FTP application
set tcp [new Agent/TCP]
$tcp set class_ 2
$ns attach-agent $node1 $tcp
set sink [new Agent/TCPSink]
$ns attach-agent $node4 $sink
$ns connect $tcp $sink
$tcp set fid_ 1

set ftp [new Application/FTP]
$ftp attach-agent $tcp
$ftp set type_ FTP

# Create UDP connection and CBR application
set udp [new Agent/UDP]
$ns attach-agent $node2 $udp
set null [new Agent/Null]
$ns attach-agent $node3 $null
$ns connect $udp $null
$udp set fid_ 2

set cbr [new Application/Traffic/CBR]
$cbr attach-agent $udp
$cbr set type_ CBR
$cbr set packet_size_ 1000
$cbr set rate_ 1mb
$cbr set random_ false

# Schedule events
$ns at 5.0 "finish"
$ns at 0.1 "$cbr start"
$ns at 1.0 "$ftp start"
$ns at 4.0 "$ftp stop"
$ns at 4.5 "$cbr stop"

# Run the simulation
$ns run
