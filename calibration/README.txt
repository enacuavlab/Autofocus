Cette application est con�u pour s'utiliser avec Paparazzi pour l'installer : http://paparazzi.enac.fr/wiki/Getting_Started

Le code que nous vous fournissons est utilisable avec Paparazzi installé et un drone donc si vous vous procurez Paparazzi, un drone, un modem vous pourrez utiliser directement notre application sans aucune modification.
Pour vos tests, voici les étapes pour tester notre application sans drone: 

1)Copier le fichier 13_05_29__10_15_23.data et 13_04_03__13_49_35.data fourni dans /paparazzi/var/logs/

2)Aller dans note code -> Shell puis dans la fonction initialise() au deux premi�res lignes d�commenter pour activer les boutons Accelerometers et Magnetometers (s'active d'eux m�me lors de la detection de donn�es Raw sur le bus mais impossible sans drone)
 
3)Dans le code -> StartUp , d�sactiver les commandes autour de Sender (simule l'envoi de messages sur le bus) et commenter imu.ListenIMU(...)

4)Lancer Paparazzi, selectionner Microjet dans la combobox A/C puis dans Session s�lectionner Simulation puis ex�cuter

5)Lancer notre application (Lancer TestShell) et s�lectionner l'id du drone lanc� (Vous devriez voir 5 : id du Microjet) ce qui lancera deux panels indiquant le nom et les modes du drone

6)Vous pouvez vous amusez � changer les modes du drone
(Pour voir si le mode change : aller dans la gcs -> en bas au milieu selectionner Settings et dans telemetry A/P vous pouvez voir le mode du drone)

(Si vous n'avez pas install� Paparazzi, vous ne pourrez pas tester 3-4-5 mais vous pourrez testez tout de m�me la vue de la calibration des acc�l�rom�tres et des magn�tom�tres si dans StartUp et Sender vous indiquez le chemin du fichier 13_05_29__10_15_23.data) 

7) Appuyer sur le bouton de votre choix et vous verrez le test

(Obsolete comme son nom l'indique est obsolete et sera retir� dans la version finale de notre application)
