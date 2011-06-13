#Zein sareko interfazeko ip-a hartu
if=wlan0

IP=`ifconfig  $if | grep 'inet addr:'| grep -v '127.0.0.1' | cut -d: -f2 | awk '{ print $1}'`

#Izen zerbitzuko ip eta portua
NS_IP=$IP
PORT=10042

JACORB_HOME="$HOME/workspace/GI3/2.Sehilabetea/jacorb-2.3.1"

cp="$JACORB_HOME/lib/jacorb.jar"
cp="$cp:$JACORB_HOME/lib/logkit-1.2.jar"
cp="$cp:$JACORB_HOME/lib/slf4j-api-1.5.6.jar"
cp="$cp:$JACORB_HOME/lib/slf4j-jdk14-1.5.6.jar"
cp="$cp:/home/unai/Grado-3/eMandoa/commons-codec-1.5-bin/commons-codec-1.5/commons-codec-1.5.jar"
cp="$cp:."
cp="$cp:$CLASSPATH"

OPTIONS="-Djacorb.home=$JACORB_HOME"
OPTIONS="$OPTIONS -Djacorb.config.dir=$JACORB_HOME"
OPTIONS="$OPTIONS -Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB"
OPTIONS="$OPTIONS -Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton"
OPTIONS="$OPTIONS -DORBInitRef.NameService=corbaloc:iiop:1.2@$IP:$PORT/NameService"
OPTIONS="$OPTIONS -DOAIAddr=$IP"

java  $OPTIONS -cp $cp $@
