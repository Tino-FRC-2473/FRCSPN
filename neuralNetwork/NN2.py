import numpy as np
import tensorflow as tf
import matplotlib
matplotlib.use('TkAgg')
import matplotlib.pyplot as plt
import math


inputFile = open("more_data.txt")
numData = 3350
dim = [numData,54]
numStats = 9
inpRaw = []
c = 0
for line in inputFile:
	inpRaw.append(float(line.strip()))



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

def sigmoid(x):
	return 1/(1+math.exp(-x))


normalizedInp = np.zeros((len(summedInp),len(summedInp[1])))
for i in range(len(summedInp)):
	for j in range(len(summedInp[0])):
		normalizedInp[i][j]=(summedInp[i][j]-means[j])/stdDev[j]
normValInp = normalizedInp[2800:]
normalizedInp = normalizedInp[:2800]

targetsFile = open("more_results.txt")
dim = [numData,2]
outRaw = []
c = 0
for line in targetsFile:
	outRaw.append(float(line.strip()))

targets = constructArray(outRaw,len(dim),dim)
targVal = targets[2800:]
targets = targets[:2800]

i = 0
while i < len(targets):
	if targets[i][0] == 0 or targets[i][1] == 0:
		targets = np.concatenate((targets[:i],targets[(i+1):]))
		normalizedInp = np.concatenate((normalizedInp[:i],normalizedInp[(i+1):]))
	i+=1

sess = tf.InteractiveSession()

insizenum=18
numHidden = 15
numHidden1 = 7


MINI_BATCH_SIZE = 5
EPOCHS = 300
LEARNING_RATE = 5e-3
DROPOUT = 1.0
keep_prob = tf.placeholder(tf.float32)

inputs = tf.placeholder(dtype = tf.float32, shape = [None,insizenum])
targetValues = tf.placeholder(dtype=tf.float32, shape = [None,2])
in_size = tf.size(inputs)


WEIGHT_iH = weight_variable([insizenum,numHidden])
bias_H = bias_variable([numHidden])
hidden = tf.nn.relu(tf.matmul(inputs,WEIGHT_iH))+bias_H
hiddenDrop = tf.nn.dropout(hidden,keep_prob)

WEIGHT_HH = weight_variable([numHidden,numHidden1])
bias_H1 = bias_variable([numHidden1])
hidden1 = tf.nn.relu(tf.matmul(hiddenDrop,WEIGHT_HH))+bias_H1
hidden1Drop = tf.nn.dropout(hidden1,keep_prob)

WEIGHT_HTO = weight_variable([numHidden1,1])
bias_O = bias_variable([1])
outA = tf.nn.relu(tf.matmul(hidden1Drop,WEIGHT_HTO))+bias_O

inputsB = tf.concat([inputs[:,8:],inputs[:,:8]],1)

hiddenB = tf.nn.relu(tf.matmul(inputsB,WEIGHT_iH))+bias_H
hiddenBDrop = tf.nn.dropout(hiddenB,keep_prob)

hidden1B = tf.nn.relu(tf.matmul(hiddenBDrop,WEIGHT_HH))+bias_H1
hidden1BDrop = tf.nn.dropout(hidden1B,keep_prob)

outB = tf.nn.relu(tf.matmul(hidden1BDrop,WEIGHT_HTO))+bias_O

out = tf.concat([outA,outB],1)

wc = 0
WPENiH = tf.reduce_mean(tf.squared_difference(WEIGHT_iH,tf.zeros(shape=tf.shape(WEIGHT_iH))))
WPENHH = tf.reduce_mean(tf.squared_difference(WEIGHT_HH,tf.zeros(shape=tf.shape(WEIGHT_HH))))
'''WP = tf.matmul(tf.matmul(WEIGHT_iH,WEIGHT_HH),WEIGHT_HTO)
WPSD = tf.squared_difference(WP[:,1],WP[:,0])'''

'''diffO = out[:,1]-out[:,0]
diffT = targetValues[:,1]-targetValues[:,0]
diffProduct = tf.multiply(diffO,diffT)
negatives = tf.less(diffProduct,tf.ones(tf.shape(diffProduct)))
onesnzeros = tf.where(negatives, x = tf.ones(tf.shape(negatives)),y = tf.zeros(tf.shape(negatives)))
score0D = out[:,0]-targetValues[:,0]
score0sqD = tf.multiply(score0D,score0D)
score1D = out[:,1]-targetValues[:,1]
score1sqD = tf.multiply(score1D,score1D)
scoresqD = score0sqD+score1sqD
mError = tf.multiply(scoresqD,onesnzeros)
MissError = tf.reduce_mean(mError)'''

targetsA = targetValues[:,:0]
targetsB = targetValues[:,1:]

omtA = abs(outA-targetsA)
omtB = abs(outB-targetsB)
divA = 1/targetsA
divB = 1/targetsB
pea = tf.matmul(tf.transpose(omtA),divA)
peb = tf.matmul(tf.transpose(omtB),divB)
totalOutputCount = tf.cast((tf.shape(omtA)[0]*2),dtype=tf.float32)
accuracy2 = 1-(tf.reduce_sum(pea)+tf.reduce_sum(peb))/totalOutputCount
accuracy3 = 1-(tf.reduce_sum(pea)+tf.reduce_sum(peb))/totalOutputCount

MissError = tf.reduce_mean(tf.squared_difference(out, targetValues))
loss = MissError+wc*(tf.reduce_mean(WPENiH)+tf.reduce_mean(WPENHH))
loss2 = tf.reduce_sum(pea)+tf.reduce_sum(peb)
#loss = MissError-wc*tf.reduce_mean(WPSD)
#loss = MissError
thisLearningRate = tf.placeholder(dtype = tf.float32)
train_step = tf.train.AdamOptimizer(thisLearningRate).minimize(loss2)
correct_prediction = tf.equal(tf.argmax(targetValues,1), tf.argmax(out,1))
accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))


sess.run(tf.global_variables_initializer())

accuracyArr = []
lossArr = []
valAccArr=[]

for i in range(len(targets)):
	for j in range(len(targets[0])):
		if(targets[i][j]==0):
			print("afadfasdf")

for i in range(1,EPOCHS+1):
	run = True
	for j in range(int(len(inp)/MINI_BATCH_SIZE)-1):
		xdata = normalizedInp[j*MINI_BATCH_SIZE:(j+1)*MINI_BATCH_SIZE]
		ydata = targets[j*MINI_BATCH_SIZE:(j+1)*MINI_BATCH_SIZE]
		outLength = MINI_BATCH_SIZE
		train_step.run(feed_dict={inputs: xdata, targetValues: ydata, thisLearningRate: LEARNING_RATE, keep_prob: DROPOUT})
		'''l = loss.eval(feed_dict={inputs: normalizedInp, targetValues: targets, keep_prob: 1.0})
		print(l)
		print("WeightProduct")
		print(sess.run(tf.matmul(tf.matmul(WEIGHT_iH,WEIGHT_HH),WEIGHT_HTO)))
		if j>20:
			o = sess.run(out,feed_dict={inputs: normalizedInp, targetValues: targets, keep_prob: 1.0})
			print("woo hoo")
			for k in range(50):
				print(o[k])
			print("WeightProduct")
			print(sess.run(tf.matmul(tf.matmul(WEIGHT_iH,WEIGHT_HH),WEIGHT_HTO)))
			break'''
	'''o = sess.run(out,feed_dict={inputs: normalizedInp, targetValues: targets, keep_prob: 1.0})
	print("WeightProduct")
	print(sess.run(tf.matmul(tf.matmul(WEIGHT_iH,WEIGHT_HH),WEIGHT_HTO)))
	print("Output")
	for k in range(50):
		print(o[k])
	print("onesnzeros")
	print(onesnzeros.eval(feed_dict={inputs: normalizedInp, targetValues: targets, keep_prob: 1.0}))
	print("scoresqd")
	print(scoresqD.eval(feed_dict={inputs: normalizedInp, targetValues: targets, keep_prob: 1.0}))

	w = WPSD.eval(feed_dict={inputs: normalizedInp, targetValues: targets, keep_prob: 1.0})
	print(w)
	print("WeightProduct")
	print(sess.run(tf.matmul(tf.matmul(WEIGHT_iH,WEIGHT_HH),WEIGHT_HTO)))
	o = sess.run(out,feed_dict={inputs: normalizedInp, targetValues: targets, keep_prob: 1.0})
	print("Output")
	for k in range(5):
		print(o[k])
		print(targets[k])
		print(" ")'''
	a = accuracy.eval(feed_dict={inputs: normalizedInp, targetValues: targets, keep_prob: 1.0})
	a2 = accuracy2.eval(feed_dict={inputs: normalizedInp, targetValues: targets, keep_prob: 1.0})
	valAcc = accuracy2.eval(feed_dict={inputs: normValInp, targetValues: targVal, keep_prob: 1.0})
	l2 = loss2.eval(feed_dict={inputs: normalizedInp, targetValues: targets, keep_prob: 1.0})
	l = loss.eval(feed_dict={inputs: normalizedInp, targetValues: targets, keep_prob: 1.0})
	if (a<0.5):
		sess.run(tf.global_variables_initializer())
		print("Lol we messed up so we reinitialized")
	print("Epoch ", i,"WL%: ",a," Acc (1-PE)%: ",a2,"ValidAcc: ",valAcc,"PEErr: ",l2,"SQErr: ",l)
	if i%10==0 and i>0:
		if abs(a2-accuracyArr[len(accuracyArr)-10]<0.002):
			#LEARNING_RATE*=0.95
			print("meme")

	accuracyArr.append(a2)
	lossArr.append(l2)
	valAccArr.append(valAcc)



maxAcc = 0.0
for i in accuracyArr:
	if(i>maxAcc):
		maxAcc=i
print("Maximum Accuracy %g"%maxAcc)


xPlot = []
for i in range(1,EPOCHS+1):xPlot.append(i)

plt.plot(xPlot, accuracyArr, 'b-', label='Accuracy')
plt.plot(xPlot, valAccArr, 'g-', label='ValAccuracy')
plt.xlabel('Epoch')
plt.ylabel('Loss')
plt.legend()
plt.show()
