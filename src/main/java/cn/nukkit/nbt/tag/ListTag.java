/*
 * Decompiled with CFR 0.152.
 */
package cn.nukkit.nbt.tag;

import cn.nukkit.nbt.stream.NBTInputStream;
import cn.nukkit.nbt.stream.NBTOutputStream;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

public class ListTag<T extends Tag>
extends Tag {
    private List<T> list = new ArrayList<>();
    public byte type;

    public ListTag() {
        super("");
    }

    public ListTag(String string) {
        super(string);
    }

    @Override
    public void write(NBTOutputStream nBTOutputStream) throws IOException {
        this.type = !this.list.isEmpty() ? this.list.get(0).getId() : (byte)1;
        nBTOutputStream.writeByte(this.type);
        nBTOutputStream.writeInt(this.list.size());
        for (Tag tag : this.list) {
            tag.write(nBTOutputStream);
        }
    }

    @Override
    public void load(NBTInputStream dis) throws IOException {
        type = dis.readByte();
        int size = dis.readInt();

        list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Tag tag = Tag.newTag(type, null);
            tag.load(dis);
            tag.setName("");
            list.add((T) tag);
        }
    }

    @Override
    public byte getId() {
        return 9;
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(",\n\t");
        this.list.forEach(tag -> stringJoiner.add(tag.toString().replace("\n", "\n\t")));
        return "ListTag '" + this.getName() + "' (" + this.list.size() + " entries of type " + Tag.getTagName(this.type) + ") {\n\t" + stringJoiner.toString() + "\n}";
    }

    @Override
    public void print(String string, PrintStream printStream) {
        super.print(string, printStream);
        printStream.println(string + '{');
        String string2 = string;
        string = string + "   ";
        for (Tag tag : this.list) {
            tag.print(string, printStream);
        }
        printStream.println(string2 + '}');
    }

    public ListTag<T> add(T t) {
        this.type = t.getId();
        t.setName("");
        this.list.add(t);
        return this;
    }

    public ListTag<T> add(int n, T t) {
        this.type = t.getId();
        t.setName("");
        if (n >= this.list.size()) {
            this.list.add(n, t);
        } else {
            this.list.set(n, t);
        }
        return this;
    }

    @Override
    public List<Object> parseValue() {
        ArrayList<Object> arrayList = new ArrayList<>(this.list.size());
        for (Tag tag : this.list) {
            arrayList.add(tag.parseValue());
        }
        return arrayList;
    }

    public T get(int n) {
        return this.list.get(n);
    }

    public List<T> getAll() {
        return new ArrayList<>(this.list);
    }

    public void setAll(List<T> list) {
        this.list = new ArrayList<>(list);
    }

    public void remove(T t) {
        this.list.remove(t);
    }

    public void remove(int n) {
        this.list.remove(n);
    }

    public void removeAll(Collection<T> collection) {
        this.list.remove(collection);
    }

    public int size() {
        return this.list.size();
    }

    @Override
    public Tag copy() {
        ListTag<T> res = new ListTag<>(getName());
        res.type = type;
        for (T t : list) {
            @SuppressWarnings("unchecked")
            T copy = (T) t.copy();
            res.list.add(copy);
        }
        return res;
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            ListTag listTag = (ListTag)object;
            if (this.type == listTag.type) {
                return this.list.equals(listTag.list);
            }
        }
        return false;
    }

    private static IOException a(IOException iOException) {
        return iOException;
    }
}

