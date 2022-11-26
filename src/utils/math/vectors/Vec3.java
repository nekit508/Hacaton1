package utils.math.vectors;

public class Vec3 {
    public float x, y, z;

    public Vec3(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vec3(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3 set(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vec3 setX(float v){
        x = v;
        return this;
    }

    public Vec3 setY(float v){
        y = v;
        return this;
    }

    public Vec3 setZ(float v){
        z = v;
        return this;
    }

    public Vec3 translate(float x, float y, float z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public float length(){
        return (float) Math.sqrt(x*x + y*y + z*z);
    }

    public float lengthSquared(){
        return x*x + y*y + z*z;
    }

    public Vec3 sub(Vec3 vec){
        this.x -= vec.x;
        this.y -= vec.y;
        this.z -= vec.z;
        return this;
    }

    public Vec3 add(Vec3 vec){
        this.x += vec.x;
        this.y += vec.y;
        this.z += vec.z;
        return this;
    }

    public Vec3 mul(Vec3 vec){
        this.x *= vec.x;
        this.y *= vec.y;
        this.z *= vec.z;
        return this;
    }

    public Vec3 mul(float v){
        this.x *= v;
        this.y *= v;
        this.z *= v;
        return this;
    }

    public Vec3 div(Vec3 vec){
        this.x /= vec.x;
        this.y /= vec.y;
        this.z /= vec.z;
        return this;
    }

    public Vec3 div(float v){
        this.x /= v;
        this.y /= v;
        this.z /= v;
        return this;
    }

    public Vec3 cpy(){
        return new Vec3(x, y, z);
    }

    public Vec3 normalise(){
        float l = length();
        x /= l;
        y /= l;
        z /= l;
        return this;
    }

    /**
     * a.dot(b)-> cos angle a to b
     **/
    public float dot(Vec3 b){
        return b.x*x + b.y*y + b.z*z;
    }

    @Override
    public String toString() {
        return "Vec3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
