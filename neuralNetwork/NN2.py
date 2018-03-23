import numpy as np
import tensorflow as tf
import matplotlib
import math

TRAIN = 0
USE = 1

MODE = USE
'''
	On Train network using input data from 'data.txt' and target data from 'results.txt'
	Trains a feed-forward neuralnetwork that uses information about past matches 
	from teams in competiting alliances to predict the match score.
	Network predicts the correct winner 84 percent of the time.
	Network predicts the correct score with an average percent error of 23%

	On Use mode network takes input data from 'MatchInput.txt' and prints the networks 
	predicted match score
'''

'''Uses previously saved net or creates new'''
USE_LAST_NET = True
USE_NEW = not USE_LAST_NET
SAVE_THIS = True
USE_TWO_AGO = (not USE_LAST_NET) and (not USE_NEW)

def constructArray(rawInput, numDimensions, dimensionsArray):
	if(numDimensions==1):
		return rawInput
	ret = []
	for i in range(0,dimensionsArray[0]):
		lengthA = int(len(rawInput)/dimensionsArray[0]);
		ret.append(constructArray(rawInput[i*lengthA:(i+1)*lengthA],numDimensions-1,dimensionsArray[1:]))
	return ret;

def weight_variable(shape):
	initial = tf.truncated_normal(shape, stddev=1)
	return tf.Variable(initial)

def bias_variable(shape):
	initial = tf.constant(10,dtype= tf.float32,shape=shape)
	return tf.Variable(initial)

inputFile = open("data.txt")

'''Total number of data samples'''
numData = 4193
'''Number of test data samples rest used for validation'''
NUM_TEST = 3700

dim = [numData,54]
numStats = 9
inpRaw = []
c = 0

'''Reads data file, stores data, normalized data'''

for line in inputFile:
	inpRaw.append(float(line.strip()))

inp = constructArray(inpRaw,len(dim),dim)


inp = constructArray(inpRaw,len(dim),dim)

summedInp = []
for i in range(numData):
	red = []
	blue = []
	for j in range(numStats):
		red.append(inp[i][j]+inp[i][j+numStats]+inp[i][j+numStats*2])
		blue.append(inp[i][j+numStats*3]+inp[i][j+numStats*4]+inp[i][j+numStats*5])
	summedInp.append(np.concatenate((red,blue)))

means = summedInp[0]

for i in range(1,numData):
	means+=summedInp[i]
means/=numData

variance = np.zeros([numStats*2])
for i in range(numData):
	variance+=(summedInp[i]-means)*(summedInp[i]-means)
variance/=(numData-1)
stdDev = np.zeros(variance.shape[0])
for i in range(len(stdDev)):
	stdDev[i] = math.sqrt(variance[i])

allNormalizedInp = np.zeros((len(summedInp),len(summedInp[1])))
for i in range(len(summedInp)):
	for j in range(len(summedInp[0])):
		allNormalizedInp[i][j]=(summedInp[i][j]-means[j])/stdDev[j]


'''Reads data file, stores target data, normalizes data'''

targetsFile = open("results.txt")
dim = [numData,2]
outRaw = []
c = 0
for line in targetsFile:
	outRaw.append(float(line.strip()))

targets = constructArray(outRaw,len(dim),dim)

totScore = 0

for i in range(numData):
	totScore+=targets[i][0]+targets[i][1]

meanScore = totScore/(numData*2)
varianceS = 0
for i in range(numData):
	varianceS+=(targets[i][0]-meanScore)*(targets[i][0]-meanScore)
	varianceS+=(targets[i][1]-meanScore)*(targets[i][1]-meanScore)
varianceS/=(numData-1)
stdDevS = math.sqrt(varianceS)
normalizedScores = np.zeros((len(targets),len(targets[0])))

for i in range(numData):
	normalizedScores[i][0] = (targets[i][0]-meanScore)/stdDevS
	normalizedScores[i][1] = (targets[i][1]-meanScore)/stdDevS

i = 0
while i < len(targets):
	if targets[i][0] == 0 or targets[i][1] == 0:
		targets = np.concatenate((targets[:i],targets[(i+1):]))
		allNormalizedInp = np.concatenate((allNormalizedInp[:i],allNormalizedInp[(i+1):]))
	i+=1


normValInp = allNormalizedInp[NUM_TEST:]
normalizedTrainInp = allNormalizedInp[:NUM_TEST]
targVal = targets[NUM_TEST:]
targTrain = targets[:NUM_TEST]

within = 50
wInp = []
wTarg = []
nInp = []
nTarg = []
for i in range(len(targets)):
	diff = abs(targets[i][1]-targets[i][0])
	if(diff<within):
		wInp.append(allNormalizedInp[i])
		wTarg.append(targets[i])
	else:
		nInp.append(allNormalizedInp[i])
		nTarg.append(targets[i])

sess = tf.InteractiveSession()

'''Network architecture 18 input neurons 30 first layer hidden neurons....'''

insizenum=18
numHidden = 30
numHidden1 = 20
numHidden2 = 10

'''Hyperparameters'''
MINI_BATCH_SIZE = 5
EPOCHS = 100
LEARNING_RATE = 5e-5
DROPOUT = 1.0
keep_prob = tf.placeholder(tf.float32)


'''
	First 9 input neurons represent stats for the alliance whose score
	is being determined.  Next 9 represent the opposition.  By swapping
	the order the score of the opposing alliance is predicted
'''

inputs = tf.placeholder(dtype = tf.float32, shape = [None,insizenum])
targetValues = tf.placeholder(dtype=tf.float32, shape = [None,2])
in_size = tf.size(inputs)


WEIGHT_iH = weight_variable([insizenum,numHidden])
bias_H = bias_variable([numHidden])
hidden = tf.sigmoid(tf.matmul(inputs,WEIGHT_iH)+bias_H)-0.5
hiddenDrop = tf.nn.dropout(hidden,keep_prob)

WEIGHT_HH = weight_variable([numHidden,numHidden1])
bias_H1 = bias_variable([numHidden1])
hidden1 = tf.sigmoid(tf.matmul(hiddenDrop,WEIGHT_HH)+bias_H1)-0.5
hidden1Drop = tf.nn.dropout(hidden1,keep_prob)

WEIGHT_HH2 = weight_variable([numHidden1,numHidden2])
bias_H2 = bias_variable([numHidden2])
hidden2 = tf.sigmoid(tf.matmul(hidden1Drop,WEIGHT_HH2)+bias_H2)-0.5
hidden2Drop = tf.nn.dropout(hidden2,keep_prob)

WEIGHT_HTO = weight_variable([numHidden2,1])
bias_O = bias_variable([1])
outA = tf.matmul(hidden2Drop,WEIGHT_HTO)+bias_O
outA = outA*stdDevS+meanScore

inputsB = tf.concat([inputs[:,9:],inputs[:,:9]],1)

hiddenB = tf.sigmoid(tf.matmul(inputsB,WEIGHT_iH)+bias_H)-0.5
hiddenBDrop = tf.nn.dropout(hiddenB,keep_prob)

hidden1B = tf.sigmoid(tf.matmul(hiddenBDrop,WEIGHT_HH)+bias_H1)-0.5
hidden1BDrop = tf.nn.dropout(hidden1B,keep_prob)

hidden2B = tf.sigmoid(tf.matmul(hidden1BDrop,WEIGHT_HH2)+bias_H2)-0.5
hidden2BDrop = tf.nn.dropout(hidden2B,keep_prob)

outB = tf.matmul(hidden2BDrop,WEIGHT_HTO)+bias_O
outB = outB*stdDevS+meanScore

out = tf.concat([outA,outB],1)

'''Weight penalty used to help prevent overfitting'''
wc = 10

WPENiH = tf.reduce_mean(tf.sqrt(tf.sqrt(tf.squared_difference(WEIGHT_iH,tf.zeros(shape=tf.shape(WEIGHT_iH))))))
WPENHH = tf.reduce_mean(tf.sqrt(tf.sqrt(tf.squared_difference(WEIGHT_HH,tf.zeros(shape=tf.shape(WEIGHT_HH))))))


targetsA = targetValues[:,:1]
targetsB = targetValues[:,1:]

omtA = abs(outA-targetsA)
omtB = abs(outB-targetsB)
pea = 2*omtA/(outA+targetsA)
peb = 2*omtB/(outB+targetsB)
totalOutputCount = tf.cast((tf.shape(omtA)[0]*2),dtype=tf.float32)
accuracy2 = 1-(tf.reduce_sum(pea)+tf.reduce_sum(peb))/totalOutputCount


'''Squared error loss used at times'''
MissError = tf.reduce_mean(tf.squared_difference(out, targetValues))
loss = MissError+wc*(tf.reduce_mean(WPENiH)+tf.reduce_mean(WPENHH))

'''Huber loss used other times'''
loss2 = tf.losses.huber_loss(labels=targetValues,predictions=out,delta=40)+wc*(tf.reduce_mean(WPENiH)+tf.reduce_mean(WPENHH))

'''Measures correct winner predicted accuracy'''
correct_prediction = tf.equal(tf.argmax(targetValues,1), tf.argmax(out,1))
accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))
thisLearningRate = tf.placeholder(dtype = tf.float32)
train_step = tf.train.AdamOptimizer(thisLearningRate).minimize(loss2)


sess.run(tf.global_variables_initializer())
saver = tf.train.Saver()

accuracyArr = []
lossArr = []
valAccArr=[]

'''Loads previous data set if neeeded'''
if USE_LAST_NET:
	saver.restore(sess, "./FRCSPNetLast")
elif USE_TWO_AGO:
	saver.restore(sess, "./FRCSPNetTwoAgo")

if MODE==TRAIN :
	'''Train network'''
	for i in range(1,EPOCHS+1):
		run = True
		trainingData = normalizedTrainInp
		trainingTargets = targTrain
		for j in range(int(len(trainingData)/MINI_BATCH_SIZE)-1):
			'''Train with minibatch'''
			xdata = trainingData[j*MINI_BATCH_SIZE:(j+1)*MINI_BATCH_SIZE]
			ydata = trainingTargets[j*MINI_BATCH_SIZE:(j+1)*MINI_BATCH_SIZE]
			outLength = MINI_BATCH_SIZE
			train_step.run(feed_dict={inputs: xdata, targetValues: ydata, thisLearningRate: LEARNING_RATE, keep_prob: DROPOUT})
		a = accuracy.eval(feed_dict={inputs: trainingData, targetValues: trainingTargets, keep_prob: 1.0})
		aV = accuracy.eval(feed_dict={inputs: normValInp, targetValues: targVal, keep_prob: 1.0})
		a2 = accuracy2.eval(feed_dict={inputs: trainingData, targetValues: trainingTargets, keep_prob: 1.0})
		valAcc = accuracy2.eval(feed_dict={inputs: normValInp, targetValues: targVal, keep_prob: 1.0})
		l2 = loss2.eval(feed_dict={inputs: trainingData, targetValues: trainingTargets, keep_prob: 1.0})
		l = loss.eval(feed_dict={inputs: trainingData, targetValues: trainingTargets, keep_prob: 1.0})
		'''Outputs critical values each Epoch'''
		print("Epoch ", i,"WL%: ",a,"VWL%: ",a2," Acc (1-PE)%: ",a2,"ValidAcc: ",valAcc,"Loss: ",l2,"SQErr: ",l)
		'''Prevents network from getting stuck at bad minimum'''
		if (a<0.4):
			sess.run(tf.global_variables_initializer())
			print("Reinitialized")
		accuracyArr.append(a2)
		lossArr.append(l2)
		valAccArr.append(valAcc)


	'''Confusion matrix of sorts, details how network fares in close and clear matches'''
	print("close matches: ",len(wInp))

	a = accuracy.eval(feed_dict={inputs: wInp, targetValues: wTarg, keep_prob: 1.0})
	a2 = accuracy2.eval(feed_dict={inputs: wInp, targetValues: wTarg, keep_prob: 1.0})
	l2 = loss2.eval(feed_dict={inputs: wInp, targetValues: wTarg, keep_prob: 1.0})
	l = loss.eval(feed_dict={inputs: wInp, targetValues: wTarg, keep_prob: 1.0})
	print("WL%: ",a," Acc (1-PE)%: ",a2,"Loss: ",l2,"SQErr: ",l)


	print("clear wins: ",len(nInp))

	a = accuracy.eval(feed_dict={inputs: nInp, targetValues: nTarg, keep_prob: 1.0})
	a2 = accuracy2.eval(feed_dict={inputs: nInp, targetValues: nTarg, keep_prob: 1.0})
	l2 = loss2.eval(feed_dict={inputs: nInp, targetValues: nTarg, keep_prob: 1.0})
	l = loss.eval(feed_dict={inputs: nInp, targetValues: nTarg, keep_prob: 1.0})
	print("WL%: ",a," Acc (1-PE)%: ",a2,"Loss: ",l2,"SQErr: ",l)

	'''Save network if needed'''
	if(SAVE_THIS):
		saver.save(sess, "./FRCSPNetTemp")
		saver.restore(sess, "./FRCSPNetLast")
		saver.save(sess, "./FRCSPNetTwoAgo")
		saver.restore(sess, "./FRCSPNetTemp")
		saver.save(sess, "./FRCSPNetLast")

	maxAcc = 0.0
	for i in accuracyArr:
		if(i>maxAcc):
			maxAcc=i
	print("Maximum Accuracy %g"%maxAcc)

if MODE == USE:
	'''Evaluate prediction'''
	inputFile = open("MatchInput.txt")
	dim = [1,54]
	numStats = 9
	inp = []

	for line in inputFile:
		inp.append(float(line.strip()))

	red = []
	blue = []
	for j in range(numStats):
		red.append(inp[j]+inp[j+numStats]+inp[j+numStats*2])
		blue.append(inp[j+numStats*3]+inp[j+numStats*4]+inp[j+numStats*5])
	summedInp = np.concatenate((red,blue))
	normalizedInp = []
	for i in range(len(summedInp)):
		normalizedInp.append((summedInp[i]-means[i])/stdDev[i])
	doubleInp = []
	doubleInp.append(normalizedInp)
	doubleInp.append(normalizedInp)
	blank = [[0,0],[0,0]]
	prediction = out.eval(feed_dict={inputs: doubleInp,keep_prob: DROPOUT})
	print(prediction[0])

