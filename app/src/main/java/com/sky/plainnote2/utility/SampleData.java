package com.sky.plainnote2.utility;

import com.sky.plainnote2.database.NoteEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleData {
    private final static String SAMPLE_1 = "This is a simple text";
    private final static String SAMPLE_2 = "Text with 2 line \n line 2";
    private final static String SAMPLE_3 =
            "The short story is a crafted form in its own right. Short stories make use of plot, resonance, and other dynamic components as in a novel, but typically to a lesser degree. While the short story is largely distinct from the novel or novella/short novel, authors generally draw from a common pool of literary techniques.\n" +
                    "\n" +
                    "Determining what exactly separates a short story from longer fictional formats is problematic. A  classic definition of a short story is that one should be able to read it in one sitting, a point most notably made in Edgar Allan Poe's essay \"The Philosophy of Composition\" (1846).[1] According to William Faulkner, a short story is character driven and a writer's job is to \"...trot along behind him with a paper and pencil trying to keep up long enough to put down what he says and does.”[2]\n" +
                    "\n" +
                    "Some authors have argued that a short story must have a strict form. Somerset Maugham thought that the short story \"must have a definite design, which includes a point of departure, a climax and a point of test; in other words, it must have a plot\". This view is however opposed by Anton Chekov who thought that a story should have neither a beginning nor an end. It should just be a \"slice of life\", presented suggestively.[3]";


    private static Date getDate(int diff) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.MILLISECOND, diff);
        return calendar.getTime();
    }


    private static int i = 0;

    public static List<NoteEntity> getNotes() {
        List<NoteEntity> noteEntities = new ArrayList<>();

        noteEntities.add(new NoteEntity("Note " + ++i, SAMPLE_1, getDate(1000)));
        noteEntities.add(new NoteEntity("Note " + ++i, SAMPLE_2, getDate(2000)));
        noteEntities.add(new NoteEntity("Note " + ++i, SAMPLE_3, getDate(3000)));

        return noteEntities;
    }
}
