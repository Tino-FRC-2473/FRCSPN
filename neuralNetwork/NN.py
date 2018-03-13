import numpy as np
import tensorflow as tf
import matplotlib.pyplot as plt


inputFile = open("allMedians_data.txt")
dim = [2008,48]
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

targetsFile = open("allMedians_results.txt")
dim = [2008,2]
outRaw = []
c = 0
for line in targetsFile:
	outRaw.append(float(line.strip()))

targets = constructArray(outRaw,len(dim),dim)


sess = tf.InteractiveSession()

IndivHid = 3
CombHid = 6
insizenum=48
numStats = 8
numHidden = 10
numSubOutputs = 5

MINI_BATCH_SIZE = 10
EPOCHS = 50
LEARNING_RATE = 5e-2
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

keep_prob = tf.placeholder(tf.float32)
hiddenDropped = tf.nn.dropout(hidden,keep_prob)

WEIGHT_HTO = weight_variable([numHidden,2])
out = tf.nn.relu(tf.matmul(hiddenDropped,WEIGHT_HTO))

WPEN = tf.squared_difference(WEIGHT_SOH,tf.zeros(shape=([numSubOutputs*numStats,numHidden])))
loss = tf.reduce_mean(tf.squared_difference(out, targetValues))+tf.reduce_mean(WPEN)
thisLearningRate = tf.placeholder(dtype = tf.float32)
train_step = tf.train.AdamOptimizer(thisLearningRate).minimize(loss)
correct_prediction = tf.equal(tf.argmax(targetValues,1), tf.argmax(out,1))
accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))
sess.run(tf.global_variables_initializer())

accuracyArr = []

for i in range(1,EPOCHS+1):
	for j in range(int(len(inp)/MINI_BATCH_SIZE)-1):
		xdata = inp[j*MINI_BATCH_SIZE:(j+1)*MINI_BATCH_SIZE]
		ydata = targets[j*MINI_BATCH_SIZE:(j+1)*MINI_BATCH_SIZE]
		train_step.run(feed_dict={inputs: xdata, targetValues: ydata, thisLearningRate: LEARNING_RATE, keep_prob: DROPOUT})
	a = accuracy.eval(feed_dict={inputs: inp, targetValues: targets, keep_prob:1.0})
	print("Epoch ", i," Accuracy: ",a)
	if i>1:
		if a<accuracyArr[len(accuracyArr)-1]:
			LEARNING_RATE*=0.9
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
#plt.show()