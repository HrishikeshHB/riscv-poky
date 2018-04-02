DESCRIPTION = "Build Caffe library for CNN using OpenBLAS lib"
SUMMARY = "Caffe : A fast open framework for deep learning"
HOMEPAGE = "http://caffe.berkeleyvision.org/"
LICENSE = "BSD"
PRIORITY= "optional"
SECTION = "libs"
PR = "r0"

DEPENDS = " \
    python \
    python-numpy \
    make \
    boost \
    openblas \
    protobuf-native \
    protobuf \
    glog \
    gflags \
    lmdb \
"

export STAGING_INCDIR_NATIVE
export STAGING_LIBDIR_NATIVE
export STAGING_INCDIR
export STAGING_LIBDIR

LIC_FILES_CHKSUM = "file://LICENSE;md5=91d560803ea3d191c457b12834553991"

SRCREV = "acbe051635d318fd367a5744fcfb57740b1f7b35"
SRC_URI = "git://github.com/jerryz123/riscv-caffe.git;branch=riscv"

S = "${WORKDIR}/git"


do_configure() {
           cp Makefile.config.example Makefile.config
           sed -i "/^INCLUDE_DIRS :=/c\INCLUDE_DIRS := ${STAGING_INCDIR} ${STAGING_INCDIR}/python2.7 ${STAGING_LIBDIR}/python2.7/site-packages/numpy/core/include" Makefile.config
           sed -i "/^LIBRARY_DIRS :=/c\LIBRARY_DIRS := ${STAGING_LIBDIR}" Makefile.config
           sed -i "/Q ?= @/d" Makefile.config

}

do_compile () {
           oe_runmake BLAS=open CPU_ONLY=1 USE_OPENCV=0 USE_LEVELDB=0 CUSTOM_CXX="${TARGET_PREFIX}g++ ${TOOLCHAIN_OPTIONS}" WITH_PYTHON_LAYER=0
           oe_runmake BLAS=open CPU_ONLY=1 USE_OPENCV=0 USE_LEVELDB=0 CUSTOM_CXX="${TARGET_PREFIX}g++ ${TOOLCHAIN_OPTIONS}" WITH_PYTHON_LAYER=0 py
}
do_install () {
           oe_runmake BLAS=open CPU_ONLY=1 USE_OPENCV=0 USE_LEVELDB=0 CUSTOM_CXX="${TARGET_PREFIX}g++ ${TOOLCHAIN_OPTIONS}" WITH_PYTHON_LAYER=0 DISTRIBUTE_DIR=${D}/usr distribute
}
FILES_${PN} += " \
    ${prefix}/proto/* \
    ${prefix}/python/* \
"

FILES_${PN}-dev = " \
    ${includedir} \
    ${datadir}/Caffe/*cmake \
    ${libdir}/*.so \
"