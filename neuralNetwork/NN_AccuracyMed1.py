import numpy as np
import tensorflow as tf
import matplotlib
matplotlib.use('TkAgg')
import matplotlib.pyplot as plt


inputFile = open("fixed_data.txt")
dim = [2008,54]
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
  initial = tf.truncated_normal(shape, stddev=0.1)
  return tf.Variable(initial)

inp = constructArray(inpRaw,len(dim),dim)

targetsFile = open("fixed_results.txt")
dim = [2008,2]
outRaw = []
c = 0
for line in targetsFile:
	outRaw.append(float(line.strip()))

targets = constructArray(outRaw,len(dim),dim)


sess = tf.InteractiveSession()

IndivHid = 3
CombHid = 6
insizenum = 54
numStats = 8
numHidden = 25
numHidden1 = 15
numHidden2 = 10
numSubOutputs = 5

MINI_BATCH_SIZE = 5
EPOCHS = 300
LEARNING_RATE = 6e-3
DROPOUT = 0.9

inputs = tf.placeholder(dtype = tf.float32, shape = [None,insizenum])
targetValues = tf.placeholder(dtype=tf.float32, shape = [None,2])
in_size = tf.size(inputs)
half = tf.cast(insizenum/2,dtype=tf.int32)
red_inputs = inputs[:,0:half]
blue_inputs = inputs[:,half:]
subOutputsIndivs  = []
for i in range(numStats):
	ind = [i,i+numStats,i+numStats*2]
	SiR = tf.transpose(tf.nn.embedding_lookup(tf.transpose(red_inputs), ind))
	SiB = tf.transpose(tf.nn.embedding_lookup(tf.transpose(blue_inputs), ind))
	Si = tf.concat([SiR,SiB],1)
	WEIGHT_Ri = weight_variable([3,IndivHid])
	WEIGHT_Bi = weight_variable([3,IndivHid])
	WEIGHT_Ci = weight_variable([6,CombHid])
	ri = tf.nn.relu(tf.matmul(SiR,WEIGHT_Ri))
	bi = tf.nn.relu(tf.matmul(SiB,WEIGHT_Bi))
	ci = tf.nn.relu(tf.matmul(Si,WEIGHT_Ci))
	Hi = tf.concat([ri,ci,bi],1)
	WEIGHT_HOi = weight_variable([IndivHid*2+CombHid,numSubOutputs])
	subOutputsIndivs.append(tf.nn.relu(tf.matmul(Hi,WEIGHT_HOi)))

subOutputs = []
for i in range(numStats):
	if i == 0:
		subOutputs = subOutputsIndivs[i]
	else:
		subOutputs = tf.concat([subOutputs,subOutputsIndivs[i]],1)

WEIGHT_SOH = weight_variable([numSubOutputs*numStats,numHidden])
hidden = tf.nn.relu(tf.matmul(subOutputs,WEIGHT_SOH))

# WEIGHT_SOH1 = weight_variable([numHidden, numHidden1])
# hidden1 = tf.nn.relu(tf.matmul(hidden,WEIGHT_SOH1))

WEIGHT_SOH2 = weight_variable([numHidden, numHidden2])
hidden2 = tf.nn.relu(tf.matmul(hidden,WEIGHT_SOH2))

#keep_prob = tf.placeholder(tf.float32)
#hiddenDropped = tf.nn.dropout(hidden2,keep_prob)

WEIGHT_HTO = weight_variable([numHidden2,2])
out = tf.nn.relu(tf.matmul(hidden2,WEIGHT_HTO))

#WPEN = tf.squared_difference(WEIGHT_SOH,tf.zeros(shape=([numSubOutputs*numStats,numHidden])))
loss = tf.reduce_mean(tf.squared_difference(out, targetValues))
thisLearningRate = tf.placeholder(dtype = tf.float32)
global_step = tf.Variable(0, trainable=False)
memeLearningRate = LEARNING_RATE#tf.train.exponential_decay(LEARNING_RATE, 1, 1, 0.99)
train_step = tf.train.AdamOptimizer(memeLearningRate).minimize(loss)
val1 = targetValues
#number1 = tf.argmax(targetValues,1)
val2 = out
#number2 = tf.argmax(out,1)
step1 = tf.subtract(tf.argmax(targetValues,1),tf.argmax(out,1))
sess.run(tf.global_variables_initializer())


accuracyArr = []

for i in range(1,EPOCHS+1):
	for j in range(int(len(inp)/MINI_BATCH_SIZE)-1):
		xdata = inp[j*MINI_BATCH_SIZE:(j+1)*MINI_BATCH_SIZE]
		ydata = targets[j*MINI_BATCH_SIZE:(j+1)*MINI_BATCH_SIZE]
		train_step.run(feed_dict={inputs: xdata, targetValues: ydata})
	#arrOutInd = number1.eval(feed_dict={inputs:inp, targetValues:targets})
	arrOut = val1.eval(feed_dict={inputs:inp, targetValues:targets})
	#arrPredInd = number2.eval(feed_dict={inputs:inp, targetValues:targets})
	arrPred = val2.eval(feed_dict={inputs:inp, targetValues:targets})
	summ = 0
	counter = 0
	for k in range(int(len(arrOut))-1):
	#	print(arrOutInd[k])
		#print(arrOut[k])
		#print(arrOut[k][0])
		numOut = arrOut[k][0]
		numPred = arrPred[k][0]
		numOut1 = arrOut[k][1]
		numPred1 = arrPred[k][1]
		if numOut != 0:
			summ += (1-abs(numOut-numPred)/numOut)
		else:
			counter-=1
		if numOut1 != 0:
			summ += (1-abs(numOut1-numPred1)/numOut1)
		else:
			counter-=1

	newAcc = summ/(2*len(arrOut)+counter)

	#print(correct_prediction.eval(feed_dict={inputs:inp, targetValues:targets}))
	#print(step1.eval(feed_dict={inputs:inp, targetValues:targets}))
	a = newAcc #accuracy.eval(feed_dict={inputs: inp, targetValues: targets})
	print("Epoch ", i," Accuracy: ",newAcc)
	'''if i>1:
		if a<accuracyArr[len(accuracyArr)-1]:
			LEARNING_RATE*=0.9'''
	accuracyArr.append(a)

maxAcc = 0.0
for i in accuracyArr:
	if(i>maxAcc):
		maxAcc=i

print("Maximum Accuracy %g"%maxAcc)


xPlot = []
for i in range(1,EPOCHS+1):xPlot.append(i)

plt.plot(xPlot, accuracyArr, 'b-', label='Accuracy')
plt.xlabel('Epoch')
plt.ylabel('Accuracy')
plt.legend()
plt.show()
