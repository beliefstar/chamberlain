package com.zhenxin.chamberlain.common.utils;

import java.security.SecureRandom;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xzhen
 * @created 14:33 26/02/2019
 * @description TODO
 */
public class KeyGenerator {

    private static Generator generator = new Generator();

    private static LinkedBlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(20);

    static {
        product();
    }

    private static void product() {
        Thread producer = new Thread(() -> {
            while (true) {
                String gain = generator.gain();
                try {
                    blockingQueue.put(gain);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        producer.setDaemon(true);
        producer.start();
    }

    public static String gain() {
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String gain(int size) {
        if (size > 32 || size < 0) {
            throw new IndexOutOfBoundsException();
        }
        try {
            String take = blockingQueue.take();
            int i = generator.secureRandom.nextInt(32 - size);
            return take.substring(i, i + size);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class Generator {
        private volatile SecureRandom secureRandom = new SecureRandom();
        private final char[] Digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x'};
        private final int Radix = Digits.length;
        private final ReentrantLock lock = new ReentrantLock();
        private StringBuilder getBuilder() {
            byte[] data = new byte[16];
            secureRandom.nextBytes(data);
            StringBuilder sb = new StringBuilder(32);
            long bits = (long)(data[0] & 255);
            bits = bits << 8 | (long)(data[1] & 255);
            bits = bits << 8 | (long)(data[2] & 255);
            bits = bits << 8 | (long)(data[3] & 255);
            bits = bits << 8 | (long)(data[4] & 255);
            bits = bits << 8 | (long)(data[5] & 255);
            bits = bits << 8 | (long)(data[6] & 255);
            bits = bits << 6 | (long)(data[7] & 63);
            append(bits, sb);
            bits = (long)(data[8] & 255);
            bits = bits << 8 | (long)(data[9] & 255);
            bits = bits << 8 | (long)(data[10] & 255);
            bits = bits << 8 | (long)(data[11] & 255);
            bits = bits << 8 | (long)(data[12] & 255);
            bits = bits << 8 | (long)(data[13] & 255);
            bits = bits << 8 | (long)(data[14] & 255);
            bits = bits << 4 | (long)(data[15] & 15);
            append(bits, sb);
            return sb;
        }

        private String gain() {
            lock.lock();
            StringBuilder builder;
            try {
                builder = getBuilder();
                int offset = 32 - builder.length();
                if (offset == 0) {
                    return builder.toString();
                }
                boolean insert = offset > 0;
                int abs = Math.abs(offset);
                while (abs-- > 0) {
                    int nextInt = secureRandom.nextInt(builder.length());
                    if (insert) {
                        builder.insert(nextInt, Digits[nextInt]);
                    } else {
                        builder.substring(nextInt, nextInt + 1);
                    }
                }
            } finally {
                lock.unlock();
            }
            return  builder.toString();
        }

        private void append(long i, StringBuilder sb) {
            char[] buf = new char[13];
            int charPos = 12;

            for(i = -i; i <= (long)(-Radix); i /= (long)Radix) {
                buf[charPos--] = Digits[(int)(-(i % (long)Radix))];
            }

            buf[charPos] = Digits[(int)(-i)];
            sb.append(buf, charPos, 13 - charPos);
        }
    }
}
